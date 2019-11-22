import { Observable } from 'rxjs';
import { PasswordChange } from './../models/password-change';
import { UserEditInfo } from './../models/user-edit-info';
import { API_MY_PROFILE, API_USER_PASSWORD_CHANGE, API_USER_IMAGE } from './../config/api-paths';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { 
  }

  getMyProfileData(): Observable<any> {
    return this.http.get(API_MY_PROFILE);    
  }

  editMyProfile(userEditData: UserEditInfo): Observable<any> {
    return this.http.put(API_MY_PROFILE, userEditData);
  }

  editUsersPassword(passwords: PasswordChange): Observable<any> {
    return this.http.post(API_USER_PASSWORD_CHANGE, passwords);
  }

  editUserProfileImage(image): Observable<any> {
    return this.http.post(API_USER_IMAGE, image);
  }
}
