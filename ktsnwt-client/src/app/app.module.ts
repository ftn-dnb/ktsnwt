import { LocationsComponent } from './components/locations/locations.component';
import { AddTokenInterceptor } from './config/http-interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material';
import { AgmCoreModule } from '@agm/core';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { HomeComponent } from './components/home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { AccountConfirmationComponent } from './components/account-confirmation/account-confirmation.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { EventsComponent } from './components/events/events.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AddLocationComponent } from './components/admin/add-location/add-location.component';
import { MatGoogleMapsAutocompleteModule } from '@angular-material-extensions/google-maps-autocomplete';
import { EditLocationComponent } from './components/admin/edit-location/edit-location.component';
import { AddEventComponent } from './components/add-event/add-event.component';
import { MyReservationsComponent, PaymentDialog } from './components/my-reservations/my-reservations.component';
import { PaypalComponent } from './components/paypal/paypal.component';
import { MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule } from '@angular/material';
import { ShowEventComponent } from './components/show-event/show-event.component';
import { HallSettingsComponent } from './components/admin/hall-settings/hall-settings.component';
import { SeatingDirective } from './directives/seating.directive';

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
    AddLocationComponent,
    EditLocationComponent,
    MyReservationsComponent,
    AddEventComponent,
    PaypalComponent,
    PaymentDialog,
    ShowEventComponent,
    HallSettingsComponent,
    SeatingDirective,
  ],
  imports: [
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
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false} }
  ],
  entryComponents: [
    PaymentDialog,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
