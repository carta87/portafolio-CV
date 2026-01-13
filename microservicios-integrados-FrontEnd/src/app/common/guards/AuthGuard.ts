import { CanActivate, Router } from '@angular/router';
import { inject, Injectable } from '@angular/core';
import { LoginService } from '../../servicios/auth/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  private loginService = inject(LoginService);
  private router = inject(Router);

  canActivate(): boolean {

    const token = this.loginService.userToken;

    if (!token || this.loginService.isTokenExpired(token)) {
      this.loginService.logout();
      this.router.navigate(['/seguridad/login']);
      return false;
    }

    return true;
  }
}
