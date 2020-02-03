import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { LOGIN_PATH } from './../../config/router-paths';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { PasswordChange } from '../../models/request/password-change';
import { UserEditInfo } from '../../models/request/user-edit-info';
import { User } from '../../models/response/user';
import { ToastrService } from 'ngx-toastr';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  user: User = {};

  basicInfoForm: FormGroup;
  passwordForm: FormGroup;

  constructor(private fb: FormBuilder,
              private userService: UserService, 
              private toastr: ToastrService,
              private router: Router,
              private authService: AuthService) { 

    this.createBasicInfoForm();
    this.createPasswordForm();
  }

  ngOnInit() {
    this.getUserData();
  }

  private createBasicInfoForm(): void {
    this.basicInfoForm = this.fb.group({
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'email': ['', [Validators.required, Validators.email]]
    });
  }

  private createPasswordForm(): void {
    this.passwordForm = this.fb.group({
      'oldPassword': ['', Validators.required],
      'newPassword': ['', Validators.required]
    });
  }

  private getUserData(): void {
    this.userService.getMyProfileData().subscribe(data => {
      this.user = {
        id: data.id,
        username: data.username,
        email: data.email,
        firstName: data.firstName,
        lastName: data.lastName,
        imagePath: data.imagePath
      };

      this.addValuesToFormGroups();
    }, error => {
      this.toastr.error('There was an error while getting your profile data');
    })
  }

  private addValuesToFormGroups(): void {
    this.basicInfoForm.setValue({
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email
    });
  }

  onClickSaveEdit(): void {
    const data: UserEditInfo = {
      firstName: this.basicInfoForm.controls['firstName'].value,
      lastName: this.basicInfoForm.controls['lastName'].value,
      email: this.basicInfoForm.controls['email'].value,
    };

    this.userService.editMyProfile(data).subscribe(data => {
      this.toastr.success('Your profile info has been successfully changed.');
    }, error => {
      this.toastr.error('There was an error. Your data will not be changed for now.');
    });
  }

  onClickEditPassword(): void {
    if (!this.passwordForm.valid) {
      this.toastr.warning('All fields must be filled out');
      return;
    }

    const oldPassword = this.passwordForm.controls['oldPassword'].value;
    const newPassword = this.passwordForm.controls['newPassword'].value;
    
    const passwords: PasswordChange = {
      oldPassword: oldPassword,
      newPassword: newPassword
    };

    this.userService.editUsersPassword(passwords).subscribe(data => {
      this.toastr.success('Your password has been changed. Please login again.');
      this.authService.logout();
      this.router.navigate([LOGIN_PATH]);
    }, error => {
      this.toastr.error('There was an error. Your password will remain the same');
    });
  }

  onClickImageChange(event): void {
    const image = event.target.files[0];
    const uploadData = new FormData();
    uploadData.append('file', image, image.name);

    this.userService.editUserProfileImage(uploadData).subscribe(data => {
      this.toastr.success('Your image has been successfully updated.');
    }, error => {
      this.toastr.error('There was an error while uploading your new profile image.');
    });
  }
}
