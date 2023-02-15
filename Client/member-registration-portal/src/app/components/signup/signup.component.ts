import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Member } from 'src/app/entity/member';
import { CountryService } from 'src/app/services/country.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit {

  member: Member = new Member();
  errorMessage: string = '';
  isSignUpSuccess: boolean = false;
  isSignUpFailed: boolean = false;
  memberExists: boolean = false;
  exisitngMember: any = new Member();
  validAge: number = 18;
  dobValidated = true;

  onValueChange(name: any) {
    const promise = this.registrationService.findMemberByName(name.target.value);
    console.log(name.target.value);
    console.log(promise);

    promise.subscribe((response) => {
      this.exisitngMember = response;
      this.memberExists = this.exisitngMember.name != null;
    }, (error) => {
      this.memberExists = false
    });
  }

  checkDOB(dob: Date) {
    let currentDate = new Date();
    let date = new Date(dob);
    console.log(date);

    if ((date.getDate() >= currentDate.getDate()) && (date.getMonth() >= currentDate.getMonth()) && (date.getFullYear() >= currentDate.getFullYear())) {
      this.dobValidated = false;
    }
    else {
      this.dobValidated = true;
    }
  }

  validateDate(date: Date) {
    let currentDate = new Date();
    let dob = new Date(date)
    let timeDiff = Math.floor(currentDate.getTime() - dob.getTime());
    this.validAge = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
  }

  validate(signupForm) {
    if (signupForm.valid) {
      this.signup(signupForm);
    }
  }

  signup(signupForm: NgForm) {
    const promise = this.registrationService.signupMember(signupForm.value);
    promise.subscribe((response) => {
      this.isSignUpSuccess = true;
      this.errorMessage = '';
      alert('The member is registered successfully with Id: ' + response);
      signupForm.resetForm();
      this.router.navigate(['login'])

    }, (error) => {
      console.log(error);
      this.isSignUpFailed = true;
      if (error.status == 403) {
        this.errorMessage = error.error;
      }
      alert('something is wrong, try again');

    });
  }
  constructor(private registrationService: RegistrationService, private router: Router, private countryService: CountryService) { }

  ngOnInit() {
  }

}