import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { EventsComponent } from './components/events/events.component';
import { LocationsComponent } from './components/locations/locations.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { HOME_PATH, LOGIN_PATH, REGISTRATION_PATH, VERIFY_ACCOUNT_PATH, USER_SETTINGS_PATH, LOCATIONS_PATH, EVENTS_PATH } from './config/router-paths';
import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';


const routes: Routes = [
  { path: HOME_PATH, component: HomeComponent },
  { path: LOGIN_PATH, component: LoginComponent },
  { path: REGISTRATION_PATH, component: RegistrationComponent },
  { path: VERIFY_ACCOUNT_PATH, component: AccountConfirmationComponent },
  { path: USER_SETTINGS_PATH, component: UserSettingsComponent },
  { path: LOCATIONS_PATH, component: LocationsComponent },
  { path: EVENTS_PATH, component: EventsComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
