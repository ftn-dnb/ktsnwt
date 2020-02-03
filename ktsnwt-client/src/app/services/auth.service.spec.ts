import { USER_ID_KEY } from './../config/local-storage-keys';
import { API_LOGIN } from './../config/api-paths';
import { LoginInfo } from '../models/request/login-info';
import { TestBed, getTestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

describe('AuthService', () => {
  
  let injector: TestBed;
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AuthService],
      imports: [HttpClientTestingModule]
    });

    injector = getTestBed();
    service = injector.get(AuthService);
    httpMock = injector.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('login should return the user info', () => {
    const loginInfo: LoginInfo = {
      username: 'jane.doe',
      password: '123'
    };

    service.login(loginInfo).subscribe(data => {
      expect(data.username).toEqual(loginInfo.username);
    });

    const req = httpMock.expectOne(API_LOGIN);
    expect(req.request.method).toBe('POST');
    req.flush(loginInfo);
  });

  it('login should return error', () => {
    const loginInfo: LoginInfo = {
      username: 'randomusername',
      password: 'randompassword'
    };

    service.login(loginInfo).subscribe(data => {
      expect(data.status).toEqual(400);
      expect(data.statusText).toEqual('Bad credentials');
    });

    const req = httpMock.expectOne(API_LOGIN);
    expect(req.request.method).toBe('POST');
    req.flush({
      status: 400, 
      statusText: 'Bad credentials'
    });
  });

  it('isUserLoggedIn should return true', () => {
    localStorage.setItem(USER_ID_KEY, 'randomuserid');
    const result = service.isUserLoggedIn();
    expect(result).toBeTruthy();
  });

  it('isUserLoggedIn should return false', () => {
    localStorage.setItem(USER_ID_KEY, 'randomuserid');
    const result = service.isUserLoggedIn();
    expect(result).toBeTruthy();
  });

  afterEach(() => {
    localStorage.clear();
    httpMock.verify();
  });
});
