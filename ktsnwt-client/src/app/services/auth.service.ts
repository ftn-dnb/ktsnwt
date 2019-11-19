import { API_LOGIN } from './../config/api-paths';
import { Observable } from 'rxjs';
import { LoginInfo } from './../models/LoginInfo';
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

  logout(): void {
    localStorage.removeItem(USER_ID_KEY);
    localStorage.removeItem(USER_ROLE_KEY);
    localStorage.removeItem(USERNAME_KEY);
    localStorage.removeItem(USER_TOKEN_KEY);
  }
}
