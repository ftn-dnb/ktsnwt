import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventService } from './../../services/event.service';
import {Component, Inject, OnInit} from '@angular/core';
import { ADD_EVENT_PATH } from 'src/app/config/router-paths';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {PaymentDialog} from '../my-reservations/my-reservations.component';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {

  events: any[] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  totalNumOfEvents: number = 0;

  constructor(private eventService: EventService,
              private toastr: ToastrService,
              private router: Router,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.getEvents();
  }

  private getEvents(): void {
    this.eventService.getEventsOnePage(this.pageNum, +this.pageSize).subscribe(data => {
      this.totalNumOfEvents = data.totalElements;
      this.events = data.content;
    }, error => {
      this.toastr.error('There was an error while getting the data about events.');
    });
  }

  onClickAddEvent(): void {
    this.router.navigate([ADD_EVENT_PATH]);
  }

  onClickDetails(eventId: number): void {
    // TODO: Implementirati odlazak na stranicu gde se prikazuju informacije o 1 manifestaciji
    console.log("DETAILS", eventId);
  }

  onClickEdit(eventId: number): void {
    // TODO: implementirati odlazak na stranicu za izmenu podataka o manifestaciji
    console.log("EDIT", eventId);
  }

  onClickStats(eventId: number): void {
    this.openDialog(eventId);
    console.log("STATS", eventId);
  }

  onClickArchive(eventId: number): void {
    // TODO: Implementirati
    console.log("ARCHIVE", eventId);
  }

  onPageSizeSelect(): void {
    this.pageNum = 0;
    this.getEvents();
  }

  onClickNext(): void {
    this.pageNum++;
    this.getEvents();
  }

  onClickPrevious(): void {
    this.pageNum--;
    this.getEvents();
  }

  private openDialog(eventId: number) {
    this.dialog.open(EventReportDialog, {
      maxHeight: '500px',
      data: eventId
    });
  }
}



@Component({
  selector: 'event-report-dialog',
  template: `
      
      <h3 id="title"> Report for event: {{this.data}}</h3>
      <app-event-report [eventId]="this.data" ></app-event-report>
      <button mat-button (click)="onClickExit()">
        <mat-icon>close</mat-icon>
        <span>Cancel</span>
      </button>
    `
})
export class EventReportDialog {

  constructor(private toast: ToastrService,
              public dialogRef: MatDialogRef<EventReportDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onClickExit(): void {
    this.dialogRef.close();
  }

}
