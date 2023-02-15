import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  username: string;

  navSelected: string = location.href.split('/')[location.href.split('/').length - 1];
  public isLoggedIn() {
    this.username = this.jwtService.getName();
    return this.jwtService.getToken();
  }

  public logout() {
    window.sessionStorage.clear();
    this.router.navigate(['']);
  }

  constructor(private router: Router,
    public registrationService: RegistrationService,
    private jwtService: JwtClientService) { }

  ngOnInit(): void {
  }

}
