import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {AddEventComponent} from './add-event.component';
import 'zone.js/dist/zone-testing';
import {of} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";
import {ToastrModule} from "ngx-toastr";
import {EventService} from "../../services/event.service";
import {LocationService} from "../../services/location.service";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {MatDatepickerModule, MatNativeDateModule} from "@angular/material";
import '@angular/forms';
import { By } from '@angular/platform-browser';
import {by, element} from "protractor";


describe('AddEventComponent', () => {
  let component: AddEventComponent;
  let fixture: ComponentFixture<AddEventComponent>;

  beforeEach(async(() => {
    const authServiceMock = {
      login: jasmine.createSpy('login').and.returnValue(of({
        username: 'jane.doe',
        email: 'jane@doe.com'
      })),

      isUserLoggedIn: jasmine.createSpy('isUserLoggedIn').and.returnValue(false)
    };

    const eventServiceMock = {};

    const locationServiceMock = {
      getAllLocationNames: jasmine.createSpy('getAllLocationNames').and.returnValue(of(['SPENS'])),
      getLocationByName: jasmine.createSpy('getLocationByName').and.returnValue(of(
        {id: 1, name: "SPENS", address: {}, halls:
            [{id: 1, name:"Mala sala", sectors: [{ id: 1,name:"left", numRows:3, numColumns: 5,
                capacity:15, type: "SEATS", hallId: 1}, {id: 2, name: "main", numRow: 4,
                numColumns: 5, capacity:20, type:"SEATS", hallId: 1}, {id: 3, name: "right",
                numRows: 2, numColumns: 5, capacity: 10, type: "SEATS", hallId: 1}], locationId: 1},
              {id: 2, name: "Velika sala", sectors: [{ id: 4, name: "right", numRows: 3,
                  numColumns: 2, capacity: 6, type: "FLOOR", hallId: 2}], locationId: 1}]}))
    };

    TestBed.configureTestingModule({
      declarations: [AddEventComponent],
      providers: [
        {provide: AuthService, useValue: authServiceMock},
        {provide: EventService, useValue: eventServiceMock},
        {provide: LocationService, useValue: locationServiceMock}
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MatDatepickerModule,
        MatNativeDateModule,
        ToastrModule.forRoot()
      ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should be initialized', (() => {
    expect(component.addEventForm).toBeDefined();
    expect(component.addEventForm.invalid).toBeTruthy();
  }));


  it('form should be false when name is empty',  () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');

    component.addEventForm.controls.name.setValue('');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });

  it('form should be false when start date is empty',  () => {

    setLocation('SPENS');
    setStartDate('');
    setEndDate('3/3/2020');
    setType('Mala sala');

    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });



  it('form should be false when end date is empty', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');
    expect(component.addEventForm.invalid).toBeTruthy();
  });

  it('form should be false when purchase limit is empty', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');



    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('form should be false when tickets per user is empty', ( ) => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('form should be false when type is empty', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.hallId.setValue('Mala sala');
    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });



  it('form should be false when location is empty', () => {

    setLocation('');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');
    expect(component.addEventForm.invalid).toBeTruthy();
  });

  function setLocation(text: string) {
    fixture.whenStable().then(() => {
      let input = fixture.debugElement.query(By.css('#location-input'));
      let el = input.nativeElement;

      el.value = text;
      el.dispatchEvent(new Event('input'));
    });
  }

  function setStartDate(text: string) {
    fixture.whenStable().then(() => {
      let input = fixture.debugElement.query(By.css('#start-date-input'));
      let el = input.nativeElement;

      el.value = text;
      el.dispatchEvent(new Event('input'));
    });
  }

  function setEndDate(text: string) {
    fixture.whenStable().then(() => {
      let input = fixture.debugElement.query(By.css('#end-date-input'));
      let el = input.nativeElement;

      el.value = text;
      el.dispatchEvent(new Event('input'));
    });
  }


  function setType(text: string) {
    fixture.whenStable().then(() => {
      let input = fixture.debugElement.query(By.css('#hall-id-input'));
      let el = input.nativeElement;

      el.value = text;
      el.dispatchEvent(new Event('change'));

    });
  }



  it('form should be false when hall is empty', () => {
    fixture.detectChanges();

    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');
    expect(component.addEventForm.invalid).toBeTruthy();
  });



  it('form should be false when description is empty', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('');

    expect(component.addEventForm.invalid).toBeTruthy();
  });



  it('form should be false when start date is after end date', () => {
    setLocation('SPENS');
    setStartDate('3/3/2020');
    setEndDate('3/2/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('form should be false when tickets per user is negative number', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('-1');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });



  it('form should be false when tickets per user is string', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');

    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('3');
    component.addEventForm.controls.ticketsPerUser.setValue('string');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('form should be false when purchase limit is string', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');


    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('string');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('forgm should be false when purchase limit is negative number', () => {
    setLocation('SPENS');
    setStartDate('3/2/2020');
    setEndDate('3/3/2020');
    setType('Mala sala');

    component.addEventForm.controls.name.setValue('name');
    component.addEventForm.controls.purchaseLimit.setValue('-1');
    component.addEventForm.controls.ticketsPerUser.setValue('3');
    component.addEventForm.controls.type.setValue('SPORT');
    component.addEventForm.controls.description.setValue('description');

    expect(component.addEventForm.invalid).toBeTruthy();
  });


  it('should be initialized', () => {
    expect(component.addEventForm).toBeDefined();
    expect(component.addEventForm.invalid).toBeTruthy();
  });
});
