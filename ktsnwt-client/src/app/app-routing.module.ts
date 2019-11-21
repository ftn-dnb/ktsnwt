import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { HOME_PATH, LOGIN_PATH, REGISTRATION_PATH, VERIFY_ACCOUNT_PATH } from './config/router-paths';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';


const routes: Routes = [
  { path: HOME_PATH, component: HomeComponent },
  { path: LOGIN_PATH, component: LoginComponent },
  { path: REGISTRATION_PATH, component: RegistrationComponent },
  { path: VERIFY_ACCOUNT_PATH, component: AccountConfirmationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
