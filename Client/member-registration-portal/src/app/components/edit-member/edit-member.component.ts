import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Member } from 'src/app/entity/member';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-edit-member',
  templateUrl: './edit-member.component.html',
  styleUrls: ['./edit-member.component.css']
})
export class EditMemberComponent {
  member: Member;

  validate(signupForm) {
    if (signupForm.valid) {
      this.update(signupForm);
    }
  }

  update(signupForm: NgForm) {
    const promise = this.registrationService.updateMember(this.member);
    promise.subscribe((response) => {
      alert('The member is updated successfully with Id: ' + this.member.memberId);
      this.router.navigate([''])
    }, (error) => {
      console.log(error);
      alert('something is wrong, try again');
    });
    
    
  }
  constructor(private registrationService: RegistrationService, private router: Router, private jwtService: JwtClientService) { 
    
  }

  ngOnInit() {
    const promise = this.registrationService.findMemberByName(this.jwtService.getName());
    promise.subscribe((response: any) => {
      this.member = response;
    }, (error) => {
      console.log(error);
      
    });
  }
}
