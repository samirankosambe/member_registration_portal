import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DependentComponent } from './components/dependent/dependent.component';
import { EditMemberComponent } from './components/edit-member/edit-member.component';
import { GetClaimsComponent } from './components/get-claims/get-claims.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { SubmitClaimComponent } from './components/submit-claim/submit-claim.component';

const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "dependent", component: DependentComponent },
  { path: "claims", component: GetClaimsComponent },
  { path: "submit", component: SubmitClaimComponent },
  { path: "edit", component: EditMemberComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
