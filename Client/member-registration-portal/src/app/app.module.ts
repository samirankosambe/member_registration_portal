import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { DependentComponent } from './components/dependent/dependent.component';
import { GetClaimsComponent } from './components/get-claims/get-claims.component';
import { SubmitClaimComponent } from './components/submit-claim/submit-claim.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { AuthInterceptor } from './guard/auth.intercepter';
import { RegistrationService } from './services/registration.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { EditMemberComponent } from './components/edit-member/edit-member.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    SignupComponent,
    GetClaimsComponent,
    SubmitClaimComponent,
    DependentComponent,
    NavbarComponent,
    FooterComponent,
    EditMemberComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      useClass: AuthInterceptor,
      provide: HTTP_INTERCEPTORS,
      multi: true
    },
    RegistrationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
