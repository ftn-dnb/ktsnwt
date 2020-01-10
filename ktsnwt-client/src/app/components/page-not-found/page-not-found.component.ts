import { HOME_PATH } from './../../config/router-paths';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  constructor(private router: Router) { 
  }

  ngOnInit() {
  }

  gotoHomePage(): void {
    this.router.navigate([HOME_PATH]);
  }
}
