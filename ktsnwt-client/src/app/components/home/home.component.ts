import { ToastrService } from 'ngx-toastr';
import { EventService } from './../../services/event.service';
import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { SHOW_EVENT_DETAILED } from 'src/app/config/router-paths';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SearchEvent } from 'src/app/models/request/search-event';
import { formatDate } from '@angular/common';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  events: any[] = [];
  locations: Location[];
  private pageNum: number = 0;
  private pageSize: number = 10;
  private isLastPage: boolean = false;
  searchForm: FormGroup;

  constructor(private eventService: EventService,
              private toastr: ToastrService,
              private router: Router,
              private fb: FormBuilder,
              private locationService: LocationService) {
  }

  ngOnInit() {
    this.getEvents();
    this.createForm();
  }

  private getEvents(): void {
    this.eventService.getEventsOnePage(this.pageNum, this.pageSize).subscribe(data => {
      this.events.push(...data.content);
      this.isLastPage = data.last;
    }, error => {
      this.toastr.error('There was an error while getting the data for events');
    });
  }

  private createForm(): void {
    this.searchForm = this.fb.group({
      name : [null, ],
      searchDate: [null, Validators.required],
      type: [null, ],
      locationName: [null, ],
    });
  }

  @HostListener('window:scroll', [])
  onScrollDetectEndOfPage(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
      if (!this.isLastPage) {
        this.pageNum++;
        this.getEvents();
      }
    }
  }

  onClickSeeMore(eventId: number): void {
    this.router.navigate([SHOW_EVENT_DETAILED, eventId]);
  }

  onClickSearch(): void {
    const fixedDate = this.converDate(this.searchForm.controls.searchDate.value);
    const searchFilter: SearchEvent = {
      endDate: fixedDate,
      type: this.searchForm.controls.type.value,
      location: this.searchForm.controls.locationName.value,
      name: this.searchForm.controls.name.value
    };
    this.resetFields();
    this.eventService.searchEvents(searchFilter).subscribe(data => {
      this.events = [];
      this.events.push(...data.content);
      this.isLastPage = data.last;
    }, error => {
      this.toastr.error('There was an error searching event');
    });
  }

  private converDate(date: Date): string {
    return formatDate(date, 'dd-MM-yyyy', 'en-US');
  }

  private resetFields(): void {
    this.searchForm.controls.type.reset();
    this.searchForm.controls.name.reset();
    this.searchForm.controls.searchDate.reset();
    this.searchForm.controls.locationName.reset();
  }

  onClickReset(): void {
    this.events = [];
    this.getEvents();
  }
}
