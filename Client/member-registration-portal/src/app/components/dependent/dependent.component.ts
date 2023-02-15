import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Dependent } from 'src/app/entity/dependent';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-dependent',
  templateUrl: './dependent.component.html',
  styleUrls: ['./dependent.component.css']
})
export class DependentComponent {
  dependent: Dependent = new Dependent();
  newDependent: Dependent = new Dependent();
  dependents: Dependent[];
  editDependentFlag: boolean = false;
  addDependentFlag: boolean = false;
  memberName: String;

  editDependent(dependentId) {
    this.editDependentFlag = true;
    this.dependent = this.dependents.find(x => x.dependentId == dependentId);
  }

  setAddDependentFlag() {
    this.addDependentFlag = true;
  }

  addDependent(addDependentForm: NgForm) {
    this.newDependent = addDependentForm.value;
    this.newDependent.memberName = this.jwtService.getName();
    const promise = this.registrationService.addDependent(this.newDependent);
    promise.subscribe((response: any) => {
      this.newDependent.dependentId = response;
      this.dependents.push((this.newDependent))
      this.addDependentFlag = false;
      alert('Dependent Added Successfully');
      this.router.navigate(['dependent'])
    }, (error) => {
      console.log(error);
    });
  }

  deleteDependent(dependentId, index: any) {
    const promise = this.registrationService.deleteDependent(dependentId);
    promise.subscribe((response: any) => {
      this.dependents.splice(index, 1);
    })
  }

  validate(editDependentForm: NgForm) {
    if (editDependentForm.valid) {
      this.submitEditedDependent(this.dependent);
    } else {
      alert("Please fill all the values");
    }
  }

  submitEditedDependent(dependent: Dependent) {
    const promise = this.registrationService.updateDependent(dependent);
    promise.subscribe((response: any) => {
      this.editDependentFlag = false;
      alert('Dependent Updated Successfully');
      this.router.navigate(['dependent'])
    }, (error) => {
      console.log(error);
    });

  }

  constructor(private registrationService: RegistrationService, private jwtService: JwtClientService, private router: Router) {
    this.memberName = this.jwtService.getName();
    const promise = this.registrationService.getDependents(this.memberName);
    promise.subscribe((response: any) => {
      this.dependents = response;
    }, (error) => {
      console.log(error);
    });
  }
}
