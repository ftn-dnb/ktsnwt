import { UserInfo } from './../../models/user-info';
import { HOME_PATH, LOGIN_PATH } from './../../config/router-paths';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  username: string = '';
  password: string = '';
  repeatPassword: string = '';
  email: string = '';
  firstName: string = '';
  lastName: string = '';
  isUserInfoSent: boolean = false;

  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.toastr.warning('Please logout if you want to create a new account.', 'Warning');
      this.router.navigate([HOME_PATH]);
    }
  }

  onClickRegister(): void {
    if (this.password !== this.repeatPassword) {
      this.toastr.warning('Passwords don\'t match', 'Warning');
      return;
    }

    const userInfo: UserInfo = {
      username: this.username,
      password: this.password,
      repeatPassword: this.repeatPassword,
      email: this.email,
      firstName: this.firstName,
      lastName: this.lastName
    };

    this.authService.addNewUser(userInfo).subscribe(data => {
      this.isUserInfoSent = true;
    }, error => {
      this.toastr.error('There was an error while adding your account. Try again later.');
    });
  }

  onClickSignIn(): void {
    this.router.navigate([LOGIN_PATH]);
  }
}
