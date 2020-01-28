import { HOME_PATH } from './../../config/router-paths';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-page-not-authorized',
  templateUrl: './page-not-authorized.component.html',
  styleUrls: ['./page-not-authorized.component.css']
})
export class PageNotAuthorizedComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  gotoHomePage(): void {
    this.router.navigate([HOME_PATH]);
  }
}
