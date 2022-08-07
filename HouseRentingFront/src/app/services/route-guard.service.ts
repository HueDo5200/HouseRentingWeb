import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

  constructor(private router: Router, private authService: AuthService, private sessionService: SessionService) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.sessionService.getToken();
    const requiredRole = route.data['expectedRole'];
    if(!token) {
      this.router.navigate(['login']);
      return false;
    }
    // const payload: any = decode(token);
    const user = JSON.parse(this.sessionService.getUser());
    if(!this.authService.isTokenValid() || user.role.name !== requiredRole) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
