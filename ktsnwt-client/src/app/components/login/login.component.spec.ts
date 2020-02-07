import { of } from 'rxjs';
import { USERNAME_KEY } from './../../config/local-storage-keys';
import { ToastrModule } from 'ngx-toastr';
import { AuthService } from './../../services/auth.service';
import { RouterTestingModule } from '@angular/router/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { async, ComponentFixture, TestBed, tick } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {

    const authServiceMock = {
      login: jasmine.createSpy('login').and.returnValue(of({
        username: 'jane.doe',
        email: 'jane@doe.com'
      })),

      isUserLoggedIn: jasmine.createSpy('isUserLoggedIn').and.returnValue(false)
    };

    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
      imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        ToastrModule.forRoot()
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should be initialized', () => {
    expect(component.loginForm).toBeDefined();
    expect(component.loginForm.invalid).toBeTruthy();
  });

  // TODO: FIX
  it('should be true when submitted', () => {
    component.loginForm.controls.username.setValue('jane.doe');
    component.loginForm.controls.password.setValue('123');
    component.onLogin();

    expect(component.loginForm.invalid).toBeFalsy();
    // expect(component.loginSuccess).toBeTruthy();
    // expect(component.loginError).toBeFalsy();

    expect(localStorage.getItem(USERNAME_KEY)).toEqual('jane.doe');
  });

  it('should be invalid form when inputs are empty', () => {
    component.loginForm.controls.username.setValue('');
    component.loginForm.controls.password.setValue('');
    expect(component.loginForm.invalid).toBeTruthy();
  });

  it('should be invalid form when username is empty', () => {
    component.loginForm.controls.username.setValue('');
    component.loginForm.controls.password.setValue('123');
    expect(component.loginForm.invalid).toBeTruthy();
  });

  it('should be invalid form when password is empty', () => {
    component.loginForm.controls.username.setValue('jane.doe');
    component.loginForm.controls.password.setValue('');
    expect(component.loginForm.invalid).toBeTruthy();
  });
});
