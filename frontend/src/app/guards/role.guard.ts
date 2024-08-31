import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const expectedRole = next.data.expectedRole;
    const user = this.authService.currentUserValue;

    if (user && user.role === expectedRole) {
      return true; // User has the expected role
    } else {
      this.router.navigate(['/unauthorized']); // Redirect to unauthorized page
      return false; // Prevent access to the route
    }
  }
}