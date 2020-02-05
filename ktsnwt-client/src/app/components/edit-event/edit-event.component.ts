import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EventService } from 'src/app/services/event.service';
import { Event } from 'src/app/models/response/event';
import { NOT_FOUND } from 'src/app/config/router-paths';
import { EditEvent } from 'src/app/models/request/edit-event';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.css']
})
export class EditEventComponent implements OnInit {

  eventData: Event;
  editEventForm: FormGroup;
  routeSub: Subscription;

  constructor(private fb: FormBuilder,
              private router: Router,
              private toastr: ToastrService,
              private eventService: EventService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe( params => {
      this.getEventData(params.id as number);
    });
    this.createForm(this.eventData);
  }

  private createForm(data: Event): void {
    this.editEventForm = this.fb.group({
      purchaseLimit: ['', [Validators.required, Validators.min(1)]],
      ticketsPerUser: ['', [Validators.required, Validators.min(1)]],
      description: ['', Validators.required]
    });
  }

  private getEventData(id: number): void {
    this.eventService.getEventById(id).subscribe(data => {
      this.eventData = data as Event;
      this.addValuesToForm();
    }, error => {
      this.router.navigate([NOT_FOUND]);
    });
  }

  private addValuesToForm(): void {
    this.editEventForm.setValue({
      purchaseLimit: this.eventData.purchaseLimit as number,
      ticketsPerUser: this.eventData.ticketsPerUser as number,
      description: this.eventData.description
    });
  }

  onEditEventSubmit(): void {
    const newD: EditEvent = {description: this.editEventForm.controls.description.value as string,
    ticketsPerUser: this.editEventForm.controls.ticketsPerUser.value as number,
    id: this.eventData.id,
    purchaseLimit: this.editEventForm.controls.purchaseLimit.value as number};
    this.eventService.editEvent(newD).subscribe( data => {
      this.toastr.success("Event changed");
    }, error => {
      this.toastr.error("Event not changed");
    });
  }

}
