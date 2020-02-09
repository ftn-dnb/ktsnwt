import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSettingsComponent } from './user-settings.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { ToastrModule } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';
import { User } from 'src/app/models/response/user';
import { USERNAME_KEY } from 'src/app/config/local-storage-keys';

describe('UserSettingsComponent', () => {
  let component: UserSettingsComponent;
  let fixture: ComponentFixture<UserSettingsComponent>;

  beforeEach(async(() => {
    const userServiceMock = {
     getMyProfileData: jasmine.createSpy('getMyProfileData').and.returnValue(of({
      firstName: 'Jane',
      email: 'jane@doe.com',
      lastName: 'Doe'
     })),
     editMyProfile: jasmine.createSpy('editMyProfile').and.returnValue(of({
      firstName: 'Test',
      email: 'jane@doe.com',
      lastName: 'Doe',
     })),
    };

    const authServiceMock = {
      login: jasmine.createSpy('login').and.returnValue(of({
        username: 'jane.doe',
        email: 'jane@doe.com'
      })),

      isUserLoggedIn: jasmine.createSpy('isUserLoggedIn').and.returnValue(false)
    };


    TestBed.configureTestingModule({
      declarations: [ UserSettingsComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        ToastrModule.forRoot()
      ],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: AuthService, useValue: authServiceMock },
      ]
    })
    .compileComponents();
  }));


  beforeEach(() => {
    fixture = TestBed.createComponent(UserSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be initialized', () => {
    expect(component.basicInfoForm).toBeDefined();
    expect(component.basicInfoForm.invalid).toBeFalsy();

    expect(component.passwordForm).toBeDefined();
    expect(component.passwordForm.invalid).toBeTruthy();
  });

  it('should be invalid form when firstName is empty', () => {
    component.basicInfoForm.controls.firstName.setValue('');
    expect(component.basicInfoForm.invalid).toBeTruthy();
  });

  it('should be invalid form when lastName is empty', () => {
    component.basicInfoForm.controls.lastName.setValue('');
    expect(component.basicInfoForm.invalid).toBeTruthy();
  });

  it('should be invalid form when email is empty', () => {
    component.basicInfoForm.controls.email.setValue('');
    expect(component.basicInfoForm.invalid).toBeTruthy();
  });

  // kada treba da posalje validno
  it('should be true when submitted', () => {
    component.basicInfoForm.controls.firstName.setValue('Test');
    component.basicInfoForm.controls.email.setValue('jane@doe.com');
    component.basicInfoForm.controls.lastName.setValue('Doe');
    component.onClickSaveEdit();

    expect(component.basicInfoForm.invalid).toBeFalsy();
  });

  it('should be invalid form when oldPassword is empty', () => {
    component.passwordForm.controls.oldPassword.setValue('');
    component.passwordForm.controls.newPassword.setValue('123');
    expect(component.passwordForm.invalid).toBeTruthy();
  });

  it('should be invalid form when newPassword is empty', () => {
    component.passwordForm.controls.oldPassword.setValue('123');
    component.passwordForm.controls.newPassword.setValue('');
    expect(component.passwordForm.invalid).toBeTruthy();
  });

  // kada treba da promeni password
  it('should be true when submitted,', () => {
    component.passwordForm.controls.oldPassword.setValue('123');
    component.passwordForm.controls.newPassword.setValue('234');

    expect(component.passwordForm.invalid).toBeFalsy();

    component.onClickEditPassword();
    expect(localStorage.getItem(USERNAME_KEY)).toBeFalsy();
  });
});
