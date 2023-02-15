import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtResponse } from 'src/app/entity/jwt-response';
import { Member } from 'src/app/entity/member';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  member: Member = new Member();
  role: string = '';
  jwtResponse: JwtResponse;
  errorMessage: string = '';
  isLoggedIn: boolean;
  isLoginFailed: boolean;

  validate(loginForm) {
    if (loginForm.valid) {
      this.login(loginForm);
    }
  }

  login(loginForm) {
    const promise = this.registrationService.loginMember(loginForm.value);
    promise.subscribe((response: any) => {
      this.isLoggedIn = true;
      this.errorMessage = "";
      this.jwtResponse = response;
      this.jwtService.setToken(response.jwtToken);
      this.jwtService.setMemberid(response.member.memberId);
      this.jwtService.setName(response.member.name);
      this.jwtService.setCountry(response.member.country);
      this.router.navigate(['submit']);

    }, (error) => {
      this.isLoginFailed = true;
      console.log(error);
      if (error.status == 403) {
        this.errorMessage = "Please provide valid credentials";
      }

    })
  }
  constructor(private registrationService: RegistrationService,
    private router: Router,
    private jwtService: JwtClientService) { }

  ngOnInit(): void {
    if (this.jwtService.getToken()) {
      this.isLoggedIn = true;
    }
  }


}
