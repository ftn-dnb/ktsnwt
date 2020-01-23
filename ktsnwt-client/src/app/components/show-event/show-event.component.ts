import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-show-event',
  templateUrl: './show-event.component.html',
  styleUrls: ['./show-event.component.css']
})
export class ShowEventComponent implements OnInit {

  routeSub: Subscription;
  eventData: any = {};
   // namestiti da bude tip Event
  constructor(private route: ActivatedRoute,
              private eventService: EventService,
              ) {}

  ngOnInit() {
    this.routeSub = this.route.params.subscribe( params => {
        this.getEventData(params.id as number);
    });
  }

  private getEventData(id: number): void {
    this.eventService.getEventById(id).subscribe(data => {
      this.eventData = data;
      console.log(this.eventData);
    });
  }
}
