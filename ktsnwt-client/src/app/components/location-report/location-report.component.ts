import { LocationService } from 'src/app/services/location.service';
import { Component, Input, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-location-report',
  templateUrl: './location-report.component.html',
  styleUrls: ['./location-report.component.css'],
  providers: [DatePipe]
})
export class LocationReportComponent implements OnInit {

  @Input() locationId: number;
  currentYear: number = new Date().getFullYear();
  years: number[] = [];

  // daily
  maxDate = new Date();
  pickedDate: string = '';
  income: number = 0;
  soldTickets: number = 0;
  isDivHidden: boolean = true;

  // monthly
  pickedMonth: string = '';
  pickedYear: string = '';
  incomeM: number = 0;
  soldTicketsM: number = 0;
  isDivHiddenM: boolean = true;

  constructor(private datePipe: DatePipe,
              private service: LocationService) { }

  ngOnInit() {
    this.fillYears();
  }

  onClickSearch() {
    if (this.pickedDate === '') {
      return;
    }
    this.service.getDailyReport(this.transformDate(this.pickedDate), this.locationId).subscribe(data => {
      this.isDivHidden = false;
      this.income = data.valueOf().income;
      this.soldTickets = data.valueOf().ticketsSold;
    });
  }


  transformDate(date) {
    return this.datePipe.transform(date, 'yyyy-MM-dd');
  }

  onClickSearchM() {
    if (this.pickedMonth === '' || this.pickedYear === '') {
      return;
    }
    this.service.getMonthlyReport( this.pickedYear + '-' + this.transformMonth(this.pickedMonth), this.locationId).subscribe(data => {
      this.isDivHiddenM = false;
      this.incomeM = data.valueOf().income;
      this.soldTicketsM = data.valueOf().ticketsSold;
    });
  }

  fillYears() {
    for ( let i = 0; i < 12; i++) {
      this.years.push(this.currentYear - i);
    }

  }

  transformMonth(pickedMonth: string) {
    switch (pickedMonth) {
      case 'Jan' : return '01';
      case 'Feb' : return '02';
      case 'Mar' : return '03';
      case 'Apr' : return '04';
      case 'May' : return '05';
      case 'Jun' : return '06';
      case 'Jul' : return '07';
      case 'Aug' : return '08';
      case 'Sep' : return '09';
      case 'Oct' : return '10';
      case 'Nov' : return '11';
      case 'Dec' : return '12';
    }
  }

  onFocusChange() {
    this.isDivHidden = true;
    this.isDivHiddenM = true;
  }
}
