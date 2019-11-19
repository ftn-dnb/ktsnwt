import { USER_ID_KEY, USER_ROLE_KEY, USERNAME_KEY, USER_TOKEN_KEY } from './../../config/local-storage-keys';
import { LoginInfo } from './../../models/LoginInfo';
import { HOME_PATH } from './../../config/router-paths';
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

  username: string = '';
  password: string = '';

  constructor(private router: Router,
              private authService: AuthService,
              private toastr: ToastrService) { 

  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.router.navigate([HOME_PATH]);
    }
  }

  onClickLogin(): void {
    const loginInfo: LoginInfo = {
      username: this.username,
      password: this.password
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
    // @TODO: Uraditi redirekciju na stranicu za registraciju
    // this.router.navigate([REGISTER_PATH]);
    console.log("TODO: Go to registration page");
  }

}
