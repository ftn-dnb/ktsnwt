import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AgmCoreModule } from '@agm/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatGoogleMapsAutocompleteModule } from '@angular-material-extensions/google-maps-autocomplete';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddTokenInterceptor } from './interceptors/http-interceptor';
import { MaterialModule } from './material';
import { ToastrModule } from 'ngx-toastr';

import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { EventsComponent } from './components/events/events.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AddLocationComponent } from './components/admin/add-location/add-location.component';
import { EditLocationComponent } from './components/admin/edit-location/edit-location.component';
import { AddEventComponent } from './components/add-event/add-event.component';
import { MyReservationsComponent, PaymentDialog } from './components/my-reservations/my-reservations.component';
import { PaypalComponent } from './components/paypal/paypal.component';
import { ShowEventComponent } from './components/show-event/show-event.component';
import { HallSettingsComponent } from './components/admin/hall-settings/hall-settings.component';
import { PageNotAuthorizedComponent } from './components/page-not-authorized/page-not-authorized.component';
import { EventReportComponent } from './components/event-report/event-report.component';
import { EventReportDialog } from './components/events/events.component';
import { LocationReportComponent } from './components/location-report/location-report.component';
import { ReservationsComponent } from './components/reservations/reservations.component';
import { ReservationDialogComponent } from './components/reservations/dialog/reservation-dialog/reservation-dialog.component';
import { LocationReportDialog, LocationsComponent } from './components/locations/locations.component';

import { SeatingDirective } from './directives/seating.directive';
import { ReservationSeatingDirective } from './directives/reservation-seating.directive';
import { StopClickDirective } from './components/reservations/directive/stop-click.directive';
import { EditEventComponent } from './components/edit-event/edit-event.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    AccountConfirmationComponent,
    UserSettingsComponent,
    LocationsComponent,
    EventsComponent,
    PageNotFoundComponent,
    PageNotAuthorizedComponent,
    AddLocationComponent,
    EditLocationComponent,
    MyReservationsComponent,
    AddEventComponent,
    PaypalComponent,
    PaymentDialog,
    ShowEventComponent,
    HallSettingsComponent,
    SeatingDirective,
    ReservationSeatingDirective,
    StopClickDirective,
    EventReportComponent,
    EventReportDialog,
    LocationReportComponent,
    LocationReportDialog,
    ReservationsComponent,
    ReservationDialogComponent,
    EditEventComponent,
  ],
  imports: [
    MatDialogModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyASA1PH4V5-ZK3mRpFF5Yn2ZbtnMl7UnIc',
      libraries: ['places']
    }),
    MatGoogleMapsAutocompleteModule.forRoot(),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AddTokenInterceptor, multi: true },
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true} },
    { provide: MAT_DIALOG_DATA, useValue: {}}
  ],
  entryComponents: [
    PaymentDialog, EventReportDialog, LocationReportDialog, ReservationDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
