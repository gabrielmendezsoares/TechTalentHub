import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private apiUrl = 'https://your-api-url.com'; // Replace with your API URL
  private tokenKey = 'auth-token';

  // Observable for the current user
  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) {
    // Initialize current user from localStorage or empty
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser') || '{}'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  // Getter for current user
  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  // Login method
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password })
      .pipe(
        tap(response => {
          // Store the JWT token and user data in localStorage
          localStorage.setItem(this.tokenKey, response.token);
          localStorage.setItem('currentUser', JSON.stringify(response.user));
          this.currentUserSubject.next(response.user);
        })
      );
  }

  // Logout method
  logout(): void {
    // Remove the token and user data from localStorage
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  // Method to get the current token
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Check if the user is authenticated
  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}