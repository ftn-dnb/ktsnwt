import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { USER_ID_KEY, USER_ROLE_KEY, USERNAME_KEY, USER_TOKEN_KEY } from './../../config/local-storage-keys';
import { LoginInfo } from '../../models/login-info';
import { HOME_PATH, REGISTRATION_PATH } from './../../config/router-paths';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from './../../services/auth.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private fb: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private toastr: ToastrService) { 

    this.createForm();
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.router.navigate([HOME_PATH]);
    }
  }

  private createForm(): void {
    this.loginForm = this.fb.group({
      'username': ['', Validators.required],
      'password': ['', Validators.required]
    });
  }

  onLogin(): void {
    const loginInfo: LoginInfo = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    };

    this.authService.login(loginInfo).subscribe(data => {
      localStorage.setItem(USER_ID_KEY, data.id);
      localStorage.setItem(USER_ROLE_KEY, data.authorities[0]);
      localStorage.setItem(USERNAME_KEY, data.username);
      localStorage.setItem(USER_TOKEN_KEY, data.token.accessToken);
  
      this.router.navigate([HOME_PATH]);
    }, error => {
      this.toastr.warning(error.error.message, 'Warning');
    });
  }

  onClickRegister(): void {
    this.router.navigate([REGISTRATION_PATH]);
  }

}
