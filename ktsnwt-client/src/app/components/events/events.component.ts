import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventService } from './../../services/event.service';
import { Component, OnInit } from '@angular/core';

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
              private router: Router) { 
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
    // TODO: Implementirati odlazak na zasebnu stranicu za dodavanje manifestacija
    console.log("ADD EVENT");
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
    // TODO: implementirati odlazak na stranicu za prikaz statistike za datu manifestaciju
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
}
