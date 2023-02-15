import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Claim } from 'src/app/entity/claim';
import { Dependent } from 'src/app/entity/dependent';
import { Member } from 'src/app/entity/member';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-submit-claim',
  templateUrl: './submit-claim.component.html',
  styleUrls: ['./submit-claim.component.css']
})
export class SubmitClaimComponent {

  claim: Claim = new Claim();
  dobValidated = true;
  dateValidated = true;
  memberName: String;
  dependents: Dependent[];
  member: Member;
  dependent: Dependent;
  nameValidated = true;

  validate(claimForm) {
    if (claimForm.valid) {
      this.addClaim(claimForm);
    }
  }

  checkDates(admissionDate: Date, dischargeDate: Date) {
    if (admissionDate > dischargeDate) {
      this.dateValidated = false;
    }
    if (admissionDate < dischargeDate) {
      this.dateValidated = true;
    }
  }

  checkName(name: String) {
    this.dependent = this.dependents.find(x => x.name.toUpperCase() == name.toUpperCase())
    if (this.member.name.toUpperCase() == name.toUpperCase()) {
      this.nameValidated = true;
      this.claim.dob = this.member.dob;
    }
    else {
      if (this.dependent != null) {
        this.nameValidated = true;
        this.claim.dob = this.dependent.dob;
      }
      else {
        this.nameValidated = false
      }
    }
  }

  addClaim(submitForm: NgForm) {
    this.claim = submitForm.value;
    this.claim.memberName = this.jwtService.getName();
    const promise = this.registrationService.addClaim(this.claim);
    promise.subscribe((response) => {
      alert('Claim Submitted succesfully with Claim Number: ' + response);
      this.router.navigate(['claims'])
    }, (error) => {
      console.log(error);
      alert("Something went wrong, please try again later");
    })
  }

  constructor(private registrationService: RegistrationService, private router: Router, private jwtService: JwtClientService) {
    this.memberName = this.jwtService.getName();
    const promise = this.registrationService.getDependents(this.memberName);
    promise.subscribe((response: any) => {
      this.dependents = response;
    }, (error) => {
      console.log(error);
    });

    const observalbe = this.registrationService.findMemberByName(this.jwtService.getName());
    observalbe.subscribe((response: any) => {
      this.member = response;
    }, (error) => {
      console.log(error);
    });


  }

}
