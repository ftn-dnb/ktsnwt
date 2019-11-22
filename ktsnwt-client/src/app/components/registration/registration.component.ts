import { User } from './../../models/user-info';
import { HOME_PATH, LOGIN_PATH } from './../../config/router-paths';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  isUserInfoSent: boolean = false;
  registrationForm: FormGroup;

  constructor(private authService: AuthService,
              private router: Router,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.toastr.warning('Please logout if you want to create a new account.', 'Warning');
      this.router.navigate([HOME_PATH]);
    }

    this.registrationForm = new FormGroup({
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
      repeatPassword: new FormControl('', Validators.required)
    });
  }

  onRegisterSubmit(): void {
    if (!this.registrationForm.valid) {
      this.toastr.error('All fields need to be filled.');
      return;
    }

    const password = this.registrationForm.controls['password'].value;
    const repeatPassword = this.registrationForm.controls['repeatPassword'].value;

    if (password !== repeatPassword) {
      this.toastr.warning('Passwords don\'t match', 'Warning');
      return;
    }
    
    const userInfo: User = {
      username: this.registrationForm.controls['username'].value,
      password: this.registrationForm.controls['password'].value,
      repeatPassword: this.registrationForm.controls['repeatPassword'].value,
      email: this.registrationForm.controls['email'].value,
      firstName: this.registrationForm.controls['firstName'].value,
      lastName: this.registrationForm.controls['lastName'].value,
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
