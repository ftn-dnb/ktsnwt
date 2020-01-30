import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  routeSub: Subscription;
  eventDayOrdinal: number;    // ordinal, NOT id!
  eventId: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe( params => {
      this.getPathData(params.eventId as number, params.eventDay as number);
    });
  }


  private getPathData(eventId: number, eventDay: number): void {
    this.eventId = eventId;
    this.eventDayOrdinal = eventDay;
    console.log(this.eventId + " " + this.eventDayOrdinal);
  }

}
