import {Component, Input, OnInit} from '@angular/core';
import { DatePipe } from '@angular/common';
import {EventReportService} from '../../services/event-report.service';


@Component({
  selector: 'app-event-report',
  templateUrl: './event-report.component.html',
  styleUrls: ['./event-report.component.css'],
  providers: [DatePipe]
})
export class EventReportComponent implements OnInit {

  maxDate = new Date();
  pickedDate: string = '';
  income: number = 0;
  soldTickets: number = 0;
  isDivHidden: boolean = true;
  @Input() eventId: number;

  constructor(private datePipe: DatePipe,
              private service: EventReportService) { }

  ngOnInit() {

  }

  onClickSearch() {
    if (this.pickedDate === '') {
      return;
    }
    console.log('picked date : ' + this.transformDate(this.pickedDate));
    this.service.getDailyReport(this.transformDate(this.pickedDate), this.eventId).subscribe(data => {
      console.log(data.valueOf().ticketsSold);
      this.isDivHidden = false;
      this.income = data.valueOf().income;
      this.soldTickets = data.valueOf().ticketsSold;
    });
  }

  transformDate(date) {
    return this.datePipe.transform(date, 'yyyy-MM-dd');
  }
}
