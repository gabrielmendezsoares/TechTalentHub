import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      const roles = route.data['roles'] as Array<string>;
      if (roles.includes(currentUser.role)) {
        return of(true);
      } else {
        this.router.navigate(['/unauthorized']);
        return of(false);
      }
    } else {
      this.router.navigate(['/unauthorized']);
      return of(false);
    }
  }
}
