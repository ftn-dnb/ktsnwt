import { ToastrService } from 'ngx-toastr';
import { EventService } from './../../services/event.service';
import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  events: any[] = [];
  private pageNum: number = 0;
  private pageSize: number = 10;
  private isLastPage: boolean = false;

  constructor(private eventService: EventService,
              private toastr: ToastrService) { 
  }

  ngOnInit() {
    this.getEvents();
  }

  private getEvents(): void {
    this.eventService.getEventsOnePage(this.pageNum, this.pageSize).subscribe(data => {
      this.events.push(...data.content);
      this.isLastPage = data.last;
    }, error => {
      this.toastr.error('There was an error while getting the data for events');
    });
  }

  @HostListener("window:scroll", [])
  onScrollDetectEndOfPage(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.scrollHeight) {
      if (!this.isLastPage) {
        this.pageNum++;
        this.getEvents();
      }
    }
  }

  onClickSeeMore(eventId: number): void {
    // TODO: Uz pomoc rutera se navigirati na zasebnu stranicu za dati event
    console.log(eventId);
  }
}
