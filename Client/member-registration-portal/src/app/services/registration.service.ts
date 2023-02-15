import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtClientService } from './jwt-client.service';
import { Member } from '../entity/member';
import { Dependent } from '../entity/dependent';
import { Claim } from '../entity/claim';

const BASE_URL = 'http://localhost:8000';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  requestHeader = new HttpHeaders({ 'No-Auth': 'True' });

  loginMember(loginData) {
    return this.http.post(BASE_URL + "/sign-in", loginData, {
      headers: this.requestHeader
    });
  }

  signupMember(signupData) {
    console.log(signupData);

    return this.http.post(BASE_URL + "/sign-up", signupData, {
      headers: this.requestHeader, responseType: "text"
    });
  }

  findMemberByName(name) {
    return this.http.get(BASE_URL + "/find/" + name, {
      headers: this.requestHeader
    });
  }

  updateMember(member) {
    return this.http.put(BASE_URL + "/update-member", member, { responseType: "text" });
  }

  getDependents(memberName) {
    return this.http.get(BASE_URL + "/dependents/" + memberName)
  }

  addDependent(dependent) {
    return this.http.post(BASE_URL + "/add-dependent", dependent, { responseType: "text" })
  }

  updateDependent(dependent) {
    return this.http.put(BASE_URL + "/update-dependent", dependent, { responseType: "text" });
  }

  deleteDependent(dependentId) {
    return this.http.delete(BASE_URL + "/delete-dependent/" + dependentId);
  }

  addClaim(claim) {
    return this.http.post(BASE_URL + "/add-claim", claim, { responseType: "text" })
  }

  getClaims(memberName) {
    return this.http.get(BASE_URL + "/claims/" + memberName)
  }

  constructor(private http: HttpClient, private jwtService: JwtClientService) { }
}
