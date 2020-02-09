import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: './reservation-dialog.component.html',
  styleUrls: ['./reservation-dialog.component.css']
})
export class ReservationDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<ReservationDialogComponent>,
              private toastr: ToastrService) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onReserve(): void {
    if (!this.data.ticketNumber) {
      this.toastr.error('Field is empty');
      return;
    }
    if (isNaN(this.data.ticketNumber)) {
      this.toastr.error('Enter a number');
      return;
    }
    if (Number(this.data.ticketNumber) > this.data.eventInfo.ticketsPerUser) {
      this.toastr.error('Too much tickets reserved');
      return;
    }
    if (Number(this.data.ticketNumber) === 0) {
      this.toastr.error('Can not enter 0 tickets.');
      return;
    }
    if (Number(this.data.ticketNumber) > Number(this.data.remaining)) {
      this.toastr.error('Can not reserve more tickets than available.');
      return;
    }
    this.dialogRef.close(this.data.ticketNumber);
  }
}
