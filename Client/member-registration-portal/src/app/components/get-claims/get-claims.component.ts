import { Component } from '@angular/core';
import { Claim } from 'src/app/entity/claim';
import { JwtClientService } from 'src/app/services/jwt-client.service';
import { RegistrationService } from 'src/app/services/registration.service';

@Component({
  selector: 'app-get-claims',
  templateUrl: './get-claims.component.html',
  styleUrls: ['./get-claims.component.css']
})
export class GetClaimsComponent {

  claims: Claim[];

  constructor(private registrationService: RegistrationService, private jwtService: JwtClientService){
    let memberName = this.jwtService.getName();
    const promise = this.registrationService.getClaims(memberName);
    promise.subscribe((response: any) => {
      console.log(response);
      this.claims = response;
    }, (error) => {
      console.log(error);
    });

  }

}
