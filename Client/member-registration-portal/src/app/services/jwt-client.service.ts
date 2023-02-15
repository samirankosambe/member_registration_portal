import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor() { }

  public setToken(token: string) {
    window.sessionStorage.setItem('token', token);
  }

  public getToken() {
    return window.sessionStorage.getItem('token');
  }

  public setName(name: string) {
    window.sessionStorage.setItem('name', name);
  }

  public getName() {
    return window.sessionStorage.getItem('name');
  }

  public getMemberId() {
    return window.sessionStorage.getItem('memberid');
  }

  public setMemberid(memberId: string) {
    window.sessionStorage.setItem('memberid', memberId);
  }

  public setCountry(country: string) {
    window.sessionStorage.setItem('country', country);
  }

  public getCountry() {
    return window.sessionStorage.getItem('country');
  }

  isLoggedIn(role) {
    let token = this.getToken();
    if (token == undefined || token === '' || token == null) {
      return false;
    }
    else {
      return true;
    }
  }

  logout() {
    window.sessionStorage.removeItem('token');
  }

}