import { Component, OnInit } from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {MyReservationsService} from '../../services/my-reservations.service';

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.css']
})
export class MyReservationsComponent implements OnInit {

  tickets: any[] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  totalNumOfReservations: number = 0;

  constructor(private myReservationsService: MyReservationsService,
              private toastr: ToastrService,
              private router: Router) { }

  ngOnInit() {
    this.getAllTickets();
  }


  private getAllTickets(): void {
    this.myReservationsService.getReservationsOnePage(this.pageNum, +this.pageSize).subscribe(data => {
      this.tickets = data.valueOf();
      this.totalNumOfReservations = this.tickets.length;
    }, error => {
      this.toastr.error('There was an error while getting the data about reservations.');
    });
  }

  onClickCancel(id: any) {
    console.log('cancel' + id);
  }

  onClickBuy(id: any) {
    console.log('buy' + id);
  }

  onPageSizeSelect(): void {
    this.pageNum = 0;
    this.getAllTickets();
  }

  onClickNext(): void {
    this.pageNum++;
    this.getAllTickets();
  }

  onClickPrevious(): void {
    this.pageNum--;
    this.getAllTickets();
  }
}
