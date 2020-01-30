import { ToastrService } from 'ngx-toastr';
import { LocationService } from './../../services/location.service';
import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from "@angular/material";
import { Location } from '../../models/response/location';

@Component({
  selector: 'app-locations',
  templateUrl: './locations.component.html',
  styleUrls: ['./locations.component.css']
})
export class LocationsComponent implements OnInit {

  locations: Location[] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  totalNumOfLocations: number = 0;

  constructor(private locationService: LocationService,
              private toastr: ToastrService,
              private router: Router,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.getLocations();
  }

  private getLocations(): void {
    this.locationService.getLocationsOnePage(this.pageNum, +this.pageSize).subscribe(data => {
      this.locations = data.content;
      this.totalNumOfLocations = data.totalElements;
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
    this.openDialog(locationId);
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


  private openDialog(locationId: number) {
    this.dialog.open(LocationReportDialog, {
      maxHeight: '500px',
      data: locationId
    });
  }
}

@Component({
  selector: 'location-report-dialog',
  template: `
      
      <h3 id="title"> Report for location: {{this.data}}</h3>
      <app-location-report [locationId]="this.data" ></app-location-report>

      <button mat-button (click)="onClickExit()">
        <mat-icon>close</mat-icon>
        <span>Cancel</span>
      </button>
    `
})
export class LocationReportDialog {

  constructor(private toast: ToastrService,
              public dialogRef: MatDialogRef<LocationReportDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onClickExit(): void {
    this.dialogRef.close();
  }

}
