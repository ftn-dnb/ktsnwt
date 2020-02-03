import { UserRegistrationData } from '../models/request/user-registration-data';
import { API_LOGIN, API_REGISTER_USER, API_VERIFY_ACCOUNT } from './../config/api-paths';
import { Observable } from 'rxjs';
import { LoginInfo } from '../models/request/login-info';
import { USER_ID_KEY, USER_ROLE_KEY, USERNAME_KEY, USER_TOKEN_KEY } from './../config/local-storage-keys';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  isUserLoggedIn(): boolean {
    return localStorage.getItem(USER_ID_KEY) != null;
  }

  login(loginInfo: LoginInfo): Observable<any> {
    return this.http.post(API_LOGIN, loginInfo);
  }

  checkForAdmin(): boolean {
    return localStorage.getItem(USER_ROLE_KEY) === 'ROLE_ADMIN';
  }

  logout(): void {
    localStorage.removeItem(USER_ID_KEY);
    localStorage.removeItem(USER_ROLE_KEY);
    localStorage.removeItem(USERNAME_KEY);
    localStorage.removeItem(USER_TOKEN_KEY);
  }

  addNewUser(userInfo: UserRegistrationData): Observable<any> {
    return this.http.post(API_REGISTER_USER, userInfo);
  }

  activateAccount(confirmationToken: string): Observable<any> {
    return this.http.get(`${API_VERIFY_ACCOUNT}/${confirmationToken}`);
  }
}
