import { LOGIN_PATH } from './../../config/router-paths';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-account-confirmation',
  templateUrl: './account-confirmation.component.html',
  styleUrls: ['./account-confirmation.component.css']
})
export class AccountConfirmationComponent implements OnInit {

  private confirmationToken: string;

  constructor(private activatedRoute: ActivatedRoute,
              private authService: AuthService,
              private toastr: ToastrService,
              private router: Router) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.confirmationToken = params['token'];
      this.activateAccount();
    });
  }

  ngOnInit() {
  }

  private activateAccount(): void {
    this.authService.activateAccount(this.confirmationToken).subscribe(data => {
      this.toastr.success('Your account has been activated.');
      this.router.navigate([LOGIN_PATH]);
    }, error => {
      this.toastr.error('There was an error while activating your account.');
    });
  }
}
