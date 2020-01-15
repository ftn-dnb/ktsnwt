import { ToastrService } from 'ngx-toastr';
import { LocationService } from './../../services/location.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.css']
})
export class LocationsComponent implements OnInit {

  locations: [] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  totalNumOfLocations: number = 0;

  constructor(private locationService: LocationService,
              private toastr: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    // TODO: Promeniti ovo u poziv funkcije koja ce dobaviti koliko lokacija postoji u bazi. (kad se implementira na back-u)
    this.totalNumOfLocations = 20;
    this.getLocations();
  }

  private getLocations(): void {
    this.locationService.getLocationsOnePage(this.pageNum, +this.pageSize).subscribe(data => {
      this.locations = data;
    }, error => {
      this.toastr.error('There was an error while getting locations data.');
    });
  }

  onClickAddLocation(): void {
    this.router.navigate(['/add-location']);
  }

  onClickEdit(locationId: number): void {
    this.router.navigate(['/edit-location/' + locationId]);
  }

  onClickStats(locationId: number): void {
    // TODO: implementirati ovo
    // Otici na novu stranicu koja sluzi za prikazivanje statistike
    console.log("STATS location with id " + locationId);
  }

  onPageSizeSelect(): void {
    this.pageNum = 0;
    this.getLocations();
  }

  onClickNext(): void {
    this.pageNum++;
    this.getLocations();
  }

  onClickPrevious(): void {
    this.pageNum--;
    this.getLocations();
  }
}
