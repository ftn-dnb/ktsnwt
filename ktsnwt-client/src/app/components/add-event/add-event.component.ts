import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgModel, FormControl } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LocationService } from './../../services/location.service';
import { Observable } from 'rxjs';
import { EventInfo } from 'src/app/models/event-info';
import { EventService } from 'src/app/services/event.service';
import { formatDate } from '@angular/common';
import { EVENTS_PATH } from 'src/app/config/router-paths';



@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {

  addEventForm: FormGroup;
  locationName: string;
  location: any;
  halls: any[] = [];
  locationNames: string[] = [];
  filteredNames: Observable<string[]>;
  nameControl = new FormControl();
  imagePath = '';
  uploadData: FormData;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private toastr: ToastrService,
              private locationService: LocationService,
              private eventService: EventService) { }

  ngOnInit() {
    this.createForm();
    this.getLocationNames();
  }

  private createForm(): void {
    this.addEventForm = this.fb.group({
      name : ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      purchaseLimit: ['', [Validators.required, Validators.min(1)]],
      ticketsPerUser: ['', [Validators.required, Validators.min(1)]],
      description: ['', Validators.required],
      type: ['', Validators.required],
      locationName: [],
      hallId: ['', Validators.required],
      left: ['', ],
      center: ['', ],
      right: ['', ],
    }, { validator: [this.dateValidateToday, this.endDateBeforeStartDate]});
  }

  onAddEventSubmit(): void {

    const fixedStartDate = this.converDate(this.addEventForm.controls.startDate.value);
    const fixedEndDate = this.converDate(this.addEventForm.controls.endDate.value);
    const eventInfo: EventInfo = {
      name: this.addEventForm.controls.name.value,
      startDate: fixedStartDate,
      endDate: fixedEndDate,
      purchaseLimit: this.addEventForm.controls.purchaseLimit.value,
      ticketsPerUser: this.addEventForm.controls.ticketsPerUser.value,
      description: this.addEventForm.controls.description.value,
      type: this.addEventForm.controls.type.value,
      hallId: this.addEventForm.controls.hallId.value,
    };

    this.eventService.addNewEvent(eventInfo).subscribe(data => {
      console.log(data);
      this.halls.forEach(hall => {
        console.log(hall);
        if (hall.id === this.addEventForm.controls.hallId.value) {
            const pricing = [{id: hall.sectors[0].id,
                            price: this.addEventForm.controls.left.value},
                          {id: hall.sectors[1].id,
                            price: this.addEventForm.controls.center.value},
                          {id: hall.sectors[2].id,
                            price: this.addEventForm.controls.right.value}];
            this.eventService.setEventPricing(data.id, pricing).subscribe();
        }
      });
      if (this.imagePath !== '') {
        this.eventService.editEventImage(this.uploadData, data.id).subscribe(resultData => {
        }, errorImage => {
          this.toastr.error('There was an error while uploading picture');
        });
      }
      this.toastr.success('New event has been successfully added.');
      this.router.navigate([EVENTS_PATH]);
    }, error => {
      this.toastr.warning(error.error.message, 'Warning');
    });
  }

  findLocationByName(): void {
    this.locationService.getLocationByName(this.locationName).subscribe(data => {
      this.halls = data.halls;
      console.log(this.halls);
    }, error => {
      this.halls = [];
      // this.toastr.error('No location with that name');
    });
  }

  getLocationNames(): void {
    this.locationService.getAllLocationNames().subscribe(data => {
      // autocomplete
      this.locationNames = data;
    });
  }

  private converDate(date: Date): string {
    return formatDate(date, 'dd-MM-yyyy', 'en-US');
  }

  endDateBeforeStartDate(group: FormGroup): any {
    if (group) {
      if (group.get('startDate').value > group.get('endDate').value) {
        return { endBefore : true};
      }
    }
    return null;
  }

  dateValidateToday(group: FormGroup): any {
    if (group) {
      const dateToday = new Date();
      if (group.get('startDate').value < dateToday) {
        return { beforeTodayStart: true};
      } else if (group.get('endDate').value < dateToday) {
        return { beforeTodayEnd: true};
      }
    }
    return null;
  }

  onClickImageChange(event): void {
    const image = event.target.files[0];
    this.uploadData = new FormData();
    this.uploadData.append('file', image, image.name);
    this.imagePath = image.name;
  }
}
