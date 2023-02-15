import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtClientService } from '../services/jwt-client.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.jwtService.isLoggedIn(route.data['role'] as string)) {
      return true;
    }
    this.router.navigate(['login'])
    alert("You are not allowed to access this page");
    return false;
  }

  constructor(private jwtService: JwtClientService, private router: Router) { }

}
