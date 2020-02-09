import { TestBed, getTestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { User } from './../models/response/user';
import { API_MY_PROFILE } from '../config/api-paths';
import { UserEditInfo } from '../models/request/user-edit-info';

describe('UserService', () => {

  let injector: TestBed;
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
  TestBed.configureTestingModule({
    providers: [UserService],
    imports: [HttpClientTestingModule]
  });

  injector = getTestBed();
  service = injector.get(UserService);
  httpMock = injector.get(HttpTestingController);
});
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getMyProfileData should return the user info', () => {
    const user: User  = {
      id: 1,
      username: 'jane.doe',
      email: 'jane@doe.com',
      firstName: 'Jane',
      lastName: 'Doe',
      imagePath: 'path',
    };

    service.getMyProfileData().subscribe(data => {
      expect(data.username).toEqual(user.username);
    });
    const req = httpMock.expectOne(API_MY_PROFILE);
    expect(req.request.method).toBe('GET');
    req.flush(user);
  });

  it('editMyProfile should return the user info,', () => {
    const user: User = {
      id: 1,
      username: 'jane.doe',
      email: 'jane@doe.com',
      firstName: 'Mika',
      lastName: 'Doe',
      imagePath: 'path',
    };

    const sendData: UserEditInfo = {
      firstName: 'Mika',
      lastName: 'Doe',
      email: 'jane@doe.com'
    };
    service.editMyProfile(sendData).subscribe( data => {
      expect(data.firstName).toEqual(sendData.firstName);
    });
    const req = httpMock.expectOne(API_MY_PROFILE);
    expect(req.request.method).toBe('PUT');
    req.flush(user);
  });

  it('editMyProfile should return error,', () => {
    const user: User = {
      id: 1,
      username: 'jane.doe',
      email: 'jane@doe.com',
      firstName: 'Mika',
      lastName: 'Doe',
      imagePath: 'path',
    };

    const sendData: UserEditInfo = {
      firstName: '',
      lastName: 'Doe',
      email: 'jane@doe.com'
    };
    service.editMyProfile(sendData).subscribe( data => {
      expect(data.status).toEqual(400);
    });
    const req = httpMock.expectOne(API_MY_PROFILE);
    expect(req.request.method).toBe('PUT');
    req.flush({
      status: 400,
      statusText: 'Bad credentials'
    }
    );
  });

  afterEach(() => {
    localStorage.clear();
    httpMock.verify();
  });

});
