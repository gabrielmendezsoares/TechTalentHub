import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get the current JWT token from the AuthService
    const authToken = this.authService.getToken();

    // Clone the request to add the new header
    if (authToken) {
      const clonedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`
        }
      });
      // Pass the cloned request instead of the original request to the next handler
      return next.handle(clonedReq);
    } else {
      // If there's no token, just pass the original request
      return next.handle(req);
    }
  }
}