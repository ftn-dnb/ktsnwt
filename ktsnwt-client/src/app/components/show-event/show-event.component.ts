import { NOT_FOUND, SHOW_EVENT_DETAILED } from './../../config/router-paths';
import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Subscription } from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import {MatTabChangeEvent} from '@angular/material';
import {RESERVE_TICKET} from '../../config/router-paths';
import {USER_ROLE_KEY} from '../../config/local-storage-keys';
import {ROLE_USER, ROLE_ADMIN} from '../../config/user-roles-keys';
import { ToastrService } from 'ngx-toastr';
import { Event } from 'src/app/models/response/event';
import { EventDay } from 'src/app/models/response/eventDay';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-show-event',
  templateUrl: './show-event.component.html',
  styleUrls: ['./show-event.component.css']
})
export class ShowEventComponent implements OnInit {

  selectedDayOrdinal: number;
  routeSub: Subscription;
  eventData: Event;
  descriptionForm: FormGroup;

  constructor(private route: ActivatedRoute,
              private eventService: EventService,
              private toastr: ToastrService,
              private router: Router,
              private fb: FormBuilder
              ) {}

  ngOnInit() {
    this.routeSub = this.route.params.subscribe( params => {
      this.getEventData(params.id as number);
    });
  }

  private getEventData(id: number): void {
    this.eventService.getEventById(id).subscribe(data => {
      this.eventData = data as Event;
      console.log(this.eventData);
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

  isAdminLoggedIn(): boolean {
    return localStorage.getItem(USER_ROLE_KEY) === ROLE_ADMIN;
  }

  isDayActive(day: any): boolean {
    return day.status === 'ACTIVE';
  }

  isDayBeforeEvent(day: EventDay): boolean {
    const datum = new Date(day.date);
    return datum.getTime() > new Date().getTime();
  }

  onClickDisableDay(day: EventDay): void {
    this.eventService.disableEventDay(day.id).subscribe(data => {

      this.toastr.success('Event day is canceled');
      this.eventData.days.forEach(changeDay => {
        if (changeDay.id === day.id) {
          changeDay.status = 'CANCELED';
        }
      });
    }, error => {
      this.toastr.error('Event day is not canceled');
    });
  }

}
