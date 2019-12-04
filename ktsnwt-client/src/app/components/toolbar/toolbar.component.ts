import { ROLE_ADMIN } from './../../config/user-roles-keys';
import { USERNAME_KEY, USER_ROLE_KEY } from './../../config/local-storage-keys';
import { AuthService } from './../../services/auth.service';
import { HOME_PATH, LOGIN_PATH, REGISTRATION_PATH, USER_SETTINGS_PATH, LOCATIONS_PATH } from './../../config/router-paths';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService, private t: ToastrService) { 
  }

  ngOnInit() {
  }

  getUsername(): string {
    return localStorage.getItem(USERNAME_KEY);
  }

  isUserLoggedIn(): boolean {
    return this.authService.isUserLoggedIn();
  }

  isAdminLoggedIn(): boolean {
    return localStorage.getItem(USER_ROLE_KEY) === ROLE_ADMIN;
  }

  onTitleClick(): void {
    this.router.navigate([HOME_PATH]);
  }

  onClickLogin(): void {
    this.router.navigate([LOGIN_PATH]);
  }

  onClickRegister(): void {
    this.router.navigate([REGISTRATION_PATH]);
  }

  onClickProfileSettings(): void {
    this.router.navigate([USER_SETTINGS_PATH]);
  }

  onClickLogout(): void {
    this.authService.logout();
    this.router.navigate([HOME_PATH]);
  }

  onClickAllLocations(): void {
    this.router.navigate([LOCATIONS_PATH]);
  }
}
