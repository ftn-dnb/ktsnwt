import { UserRegistrationData } from '../../models/request/user-registration-data';
import { HOME_PATH, LOGIN_PATH } from './../../config/router-paths';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  isUserInfoSent: boolean = false;
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.toastr.warning('Please logout if you want to create a new account.', 'Warning');
      this.router.navigate([HOME_PATH]);
    }

    this.createForm();
  }

  private createForm(): void {
    this.registrationForm = this.fb.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'username': ['', Validators.required],
      'email': ['', [Validators.required, Validators.email]],
      'password': ['', Validators.required],
      'repeatPassword': ['', Validators.required]
    });
  }

  onRegisterSubmit(): void {
    const password = this.registrationForm.controls['password'].value;
    const repeatPassword = this.registrationForm.controls['repeatPassword'].value;

    if (password !== repeatPassword) {
      this.toastr.warning('Passwords don\'t match', 'Warning');
      return;
    }
    
    const userInfo: UserRegistrationData = {
      username: this.registrationForm.controls['username'].value,
      password: password,
      repeatPassword: repeatPassword,
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
