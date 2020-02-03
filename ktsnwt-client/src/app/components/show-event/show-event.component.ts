import { NOT_FOUND } from './../../config/router-paths';
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Subscription } from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import {MatTabChangeEvent} from '@angular/material';
import {RESERVE_TICKET} from '../../config/router-paths';
import {USER_ROLE_KEY} from '../../config/local-storage-keys';
import {ROLE_USER} from '../../config/user-roles-keys';

@Component({
  selector: 'app-show-event',
  templateUrl: './show-event.component.html',
  styleUrls: ['./show-event.component.css']
})
export class ShowEventComponent implements OnInit {

  selectedDayOrdinal: number;
  routeSub: Subscription;
  eventData: any = {};
   // namestiti da bude tip Event

  constructor(private route: ActivatedRoute,
              private eventService: EventService,
              private router: Router
              ) {}

  ngOnInit() {
    this.routeSub = this.route.params.subscribe( params => {
      this.getEventData(params.id as number);
    });
  }

  private getEventData(id: number): void {
    this.eventService.getEventById(id).subscribe(data => {
      this.eventData = data;
    }, error => {
      this.router.navigate([NOT_FOUND]);
    });
  }

  tabChanged($event: MatTabChangeEvent) {
    this.selectedDayOrdinal = $event.index;
  }

  onClickReserve() {
    let eventDayId;
    eventDayId = this.eventData.days.filter(item => item)[this.selectedDayOrdinal];
    this.router.navigate([RESERVE_TICKET, this.eventData.id, eventDayId.id]);
  }

  isPlainUserLoggedIn() {
    return localStorage.getItem(USER_ROLE_KEY) === ROLE_USER;
  }
}
