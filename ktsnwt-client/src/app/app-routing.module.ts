import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { EventsComponent } from './components/events/events.component';
import { LocationsComponent } from './components/locations/locations.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { AddLocationComponent } from './components/admin/add-location/add-location.component';
import {
  HOME_PATH, LOGIN_PATH, REGISTRATION_PATH,
  VERIFY_ACCOUNT_PATH, USER_SETTINGS_PATH, LOCATIONS_PATH,
  EVENTS_PATH, ADD_LOCATION_PATH, EDIT_LOCATION_PATH,
  MY_RESERVATIONS_PATH, ADD_EVENT_PATH, SHOW_EVENT_DETAILED_ID,
  HALL_SETTINGS, NOT_FOUND, NOT_AUTHORIZED, RESERVE_TICKET, RESERVE_TICKET_ID, EDIT_EVENT_ID
} from './config/router-paths';

import { HomeComponent } from './components/home/home.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';
import { EditLocationComponent } from './components/admin/edit-location/edit-location.component';
import { MyReservationsComponent } from './components/my-reservations/my-reservations.component';
import { AddEventComponent } from './components/add-event/add-event.component';
import { ShowEventComponent } from './components/show-event/show-event.component';
import { HallSettingsComponent } from './components/admin/hall-settings/hall-settings.component';
import { EditLocationGuard } from './guards/edit-location.guard';
import { ShowEventGuard } from './guards/show-event.guard';
import { PageNotAuthorizedComponent } from './components/page-not-authorized/page-not-authorized.component';
import { AuthGuard } from './guards/auth.guard';
import { AuthLoginGuard } from './guards/auth-login.guard';
import { ReservationsComponent } from './components/reservations/reservations.component';
import { EditEventComponent } from './components/edit-event/edit-event.component';

const routes: Routes = [
  { path: HOME_PATH, component: HomeComponent },
  { path: LOGIN_PATH, component: LoginComponent },
  { path: REGISTRATION_PATH, component: RegistrationComponent },
  { path: VERIFY_ACCOUNT_PATH, component: AccountConfirmationComponent },
  {
    path: USER_SETTINGS_PATH,
    canActivate: [AuthLoginGuard],
    component: UserSettingsComponent },
  {
    path: LOCATIONS_PATH,
    canActivate: [AuthGuard],
    component: LocationsComponent
  },
  {
    path: EVENTS_PATH,
    canActivate: [AuthGuard],
    component: EventsComponent
  },
  {
    path: ADD_LOCATION_PATH,
    canActivate: [AuthGuard],
    component: AddLocationComponent
  },
  {
    path: EDIT_LOCATION_PATH,
    canActivate: [EditLocationGuard, AuthGuard],
    component: EditLocationComponent
  },
  {
    path: MY_RESERVATIONS_PATH,
    canActivate: [AuthLoginGuard],
    component: MyReservationsComponent
  },
  {
    path: ADD_EVENT_PATH,
    canActivate: [AuthGuard],
    component: AddEventComponent
  },
  { path: SHOW_EVENT_DETAILED_ID, component : ShowEventComponent },
  {
    path: HALL_SETTINGS,
    canActivate: [AuthGuard],
    component: HallSettingsComponent
  },
  {
    path: ADD_EVENT_PATH,
    canActivate: [AuthGuard],
    component: AddEventComponent
  },
  {
    path: EDIT_EVENT_ID,
    canActivate: [AuthGuard],
    component: EditEventComponent,
  },
  {
    path: SHOW_EVENT_DETAILED_ID,
    canActivate: [ShowEventGuard, AuthGuard],
    component : ShowEventComponent,
  },
  { path: NOT_FOUND, component: PageNotFoundComponent },
  { path: NOT_AUTHORIZED, component: PageNotAuthorizedComponent} ,
  {
    path: RESERVE_TICKET_ID,
    canActivate: [AuthLoginGuard],
    component: ReservationsComponent
  },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
