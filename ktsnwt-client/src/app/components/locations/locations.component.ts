import { ToastrService } from 'ngx-toastr';
import { LocationService } from './../../services/location.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator } from '@angular/material';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.css']
})
export class LocationsComponent implements OnInit {

  locations: [] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  pageSizeOptions: number[] = [5, 10, 15, 20];

  constructor(private locationService: LocationService,
              private toastr: ToastrService) { 
  }

  ngOnInit() {
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
    // TODO: Implementirati ovo
    // Samo sa ruterom otici na novu stranicu koja vrsi dodavanje nove lokacije.
    console.log("TODO: Implement adding new location");
  }

  onClickEdit(locationId: number): void {
    // TODO: implementirati ovo
    // Otici na novu stranicu koja sluzi za editovanje
    console.log("EDIT location with id " + locationId);
  }

  onClickStats(locationId: number): void {
    // TODO: implementirati ovo
    // Otici na novu stranicu koja sluzi za prikazivanje statistike
    console.log("STATS location with id " + locationId);
  }

  onClickNext(): void {
    // Voditi racuna da je this.pageSize string !!
    console.log(this.pageSize);
  }

  onClickPrevious(): void {

  }
}
