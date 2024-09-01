import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs'; // Adicione 'of' aqui
import { catchError, map, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

interface User {
  id: number;
  username: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:3000/api';
  private userSubject = new BehaviorSubject<User | null>(null);
  public user$ = this.userSubject.asObservable();
  public isAuthenticated$ = this.user$.pipe(map(user => !!user));

  constructor(private http: HttpClient, private router: Router) {
    this.loadUser();
  }

  private loadUser(): void {
    const token = this.getToken();
    if (token) {
      this.fetchUser().subscribe();
    }
  }

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
      tap(response => {
        if (response.token) {
          localStorage.setItem('token', response.token);
          this.fetchUser().subscribe();
        }
      }),
      map(response => !!response.token),
      catchError(() => of(false))
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.userSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  getCurrentUser(): User | null {
    return this.userSubject.value;
  }
}
