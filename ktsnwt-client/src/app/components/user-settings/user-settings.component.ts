import { LOGIN_PATH } from './../../config/router-paths';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { PasswordChange } from './../../models/password-change';
import { UserEditInfo } from './../../models/user-edit-info';
import { User } from './../../models/user';
import { ToastrService } from 'ngx-toastr';
import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent implements OnInit {

  // Namesti ovo da ne prijavljuje error
  user: User = {};
  oldPassword: string = '';
  newPassword: string = '';

  constructor(private userService: UserService, 
              private toastr: ToastrService,
              private router: Router,
              private authService: AuthService) { 
  }

  ngOnInit() {
    this.getUserData();
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
    }, error => {
      this.toastr.error('There was an error while getting your profile data');
    })
  }

  onClickSaveEdit(): void {
    // TODO: provere da neko polje nije ostalo prazno
    // proveriti firstName, lastName i email

    const data: UserEditInfo = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email
    };

    this.userService.editMyProfile(data).subscribe(data => {
      this.toastr.success('Your profile info has been successfully changed.');
    }, error => {
      this.toastr.error('There was an error. Your data will not be changed for now.');
    });
  }

  onClickEditPassword(): void {
    // TODO: Mozda dodati FormGroup ovde sa Validators.required ??
    
    if (this.oldPassword == '') {
      this.toastr.warning('Old password can not be empty.');
      return;
    }

    if (this.newPassword == '') {
      this.toastr.warning('New password can not be empty.');
      return;
    }

    const passwords: PasswordChange = {
      oldPassword: this.oldPassword,
      newPassword: this.newPassword
    };

    this.userService.editUsersPassword(passwords).subscribe(data => {
      this.toastr.success('Your password has been changed. Please login again.');
      this.authService.logout();
      this.router.navigate([LOGIN_PATH]);
    }, error => {
      this.toastr.error('There was an error. Your password will remain the same');
    });
  }

  onClickImageChange(): void {
    // TODO menjanje slike odavde
  }
}
