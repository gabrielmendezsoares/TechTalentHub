import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators'; // Importar a função map do RxJS
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:3000/aut';
  private userSubject = new BehaviorSubject<User | null>(null);

  constructor(private http: HttpClient, private router: Router) { }

  private fetchUser(): Observable<User | null> {
    return this.http.get<User>(`${this.apiUrl}/me`).pipe(
      tap(user => this.userSubject.next(user)),
      catchError(() => {
        this.userSubject.next(null);
        return of(null);
      })
    );
  }

  login(credentials: { username: string, password: string; }): Observable<boolean> {
    return this.http.post<{ token: string; }>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => this.handleLoginResponse(response)),
      map(response => !!response.token),
      catchError(() => of(false))
    );
  }

  private handleLoginResponse(response: { token: string; }): void {
    if (response.token) {
      this.storeToken(response.token);
      this.fetchUser().subscribe();
    }
  }

  private storeToken(token: string): void {
    localStorage.setItem('token', token);
  }

  logout(): void {
    this.clearToken();
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  private clearToken(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): Observable<boolean> {
    const token = this.getToken();
    return of(!!token);
  }

  getCurrentUser(): Observable<User | null> {
    return this.userSubject.asObservable();
  }
}
