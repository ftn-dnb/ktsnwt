import { User } from './../models/response/user';
import { Observable } from 'rxjs';
import { PasswordChange } from '../models/request/password-change';
import { UserEditInfo } from '../models/request/user-edit-info';
import { API_MY_PROFILE, API_USER_PASSWORD_CHANGE, API_USER_IMAGE } from './../config/api-paths';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getMyProfileData(): Observable<User> {
    return this.http.get(API_MY_PROFILE);
  }

  editMyProfile(userEditData: UserEditInfo): Observable<any> {
    return this.http.put(API_MY_PROFILE, userEditData);
  }

  editUsersPassword(passwords: PasswordChange): Observable<any> {
    return this.http.post(API_USER_PASSWORD_CHANGE, passwords);
  }

  editUserProfileImage(image: FormData): Observable<any> {
    return this.http.post(API_USER_IMAGE, image);
  }
}
