import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap, map, switchMap } from 'rxjs/operators';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';
  private userSubject = new BehaviorSubject<User | null>(null);

  constructor(private http: HttpClient, private router: Router) { }

  private fetchUser(email: string): Observable<User | null> {
    const headers = new HttpHeaders().set('Authorization', `Basic ${this.getBasicAuthToken()}`);
    const url = `${this.apiUrl}/users/email/${email}`;

    return this.http.get<User>(url, { headers }).pipe(
      tap(user => this.userSubject.next(user)),
      catchError(() => {
        this.userSubject.next(null);
        return of(null);
      })
    );
  }

  login(credentials: { email: string, password: string; }): Observable<boolean> {
    console.log(credentials);


    return this.fetchUser(credentials.email).pipe(
      switchMap(user => {
        if (user && user.email === credentials.email && user.password === credentials.password) {
          const token = btoa(`${credentials.email}:${credentials.password}`);
          this.handleLoginResponse(token, credentials.email);
          return of(true);
        } else {
          return of(false);
        }
      }),
      catchError(() => of(false))
    );
  }

  register(user: User): Observable<boolean> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Basic ${this.getBasicAuthToken()}`
    });

    return this.http.post<any>(`${this.apiUrl}/users`, JSON.stringify(user), { headers });
  }

  private handleLoginResponse(token: string, email: string): void {
    if (token) {
      this.storeToken(token);
      this.fetchUser(email).subscribe();
    }
  }

  private storeToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  private getBasicAuthToken(): string | null {
    const username = 'user';
    const password = 'password';
    return btoa(`${username}:${password}`);
  }

  logout(): void {
    this.clearToken();
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  private clearToken(): void {
    localStorage.removeItem('authToken');
  }

  getUser(): Observable<User | null> {
    return this.userSubject.asObservable();
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  isAuthenticated(): Observable<boolean> {
    return of(!!this.getBasicAuthToken());
  }

  getCurrentUser(): User | null {
    return this.userSubject.value;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user ? user.role.includes('ADMIN') : false;
  }
}