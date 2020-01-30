import { TicketService } from './../../services/ticket.service';
import { TicketBuyingData } from './../../models/ticket-buying-data';
import { Ticket } from './../../models/ticket';
import { Component, OnInit, Inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-my-reservations',
  templateUrl: './my-reservations.component.html',
  styleUrls: ['./my-reservations.component.css']
})
export class MyReservationsComponent implements OnInit {

  tickets: Ticket[] = [];
  pageSize: string = '5';
  pageNum: number = 0;
  totalNumOfReservations: number = 0;

  constructor(private ticketService: TicketService,
              private toastr: ToastrService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.getAllTickets();
  }

  private getAllTickets(): void {
    this.ticketService.getReservationsOnePage(this.pageNum, +this.pageSize).subscribe(data => {
      
      // TODO: Nacin dobavljanja treba ispraviti kada se na back-u namesti da se salje pageable a ne obinca lista 

      this.tickets = data.valueOf();
      this.totalNumOfReservations = this.tickets.length;
    }, error => {
      this.toastr.error('There was an error while getting the data about reservations.');
    });
  }

  onClickCancel(id: any) {
    this.ticketService.cancelTicket(id).subscribe(data => {
      this.toastr.success('Your ticket has been canceled!');
      this.getAllTickets();
    }, error => {
      this.toastr.error(error.error.message);
    });
  }

  onClickBuy(id: number, price: number, date: string) {
    this.openDialog(id, price, date);
  }

  private openDialog(ticketId: number, price: number, date: string): void {
    const ticketData: TicketBuyingData = {
      ticketId: ticketId,
      price: price,
      date: date
    };

    const dialogRef = this.dialog.open(PaymentDialog, {
      maxHeight: '500px',
      data: { ticket: ticketData }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllTickets();
    })
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


@Component({
  selector: 'payment-dialog',
  template: `
      <app-paypal [ticketData]="data.ticket" (paymentSuccessfull)="onPaymentSuccessfull($event)"></app-paypal>

      <button mat-button (click)="onClickExit()">
        <mat-icon>close</mat-icon>
        <span>Cancel</span>
      </button>
    `
})
export class PaymentDialog {

  constructor(private toast: ToastrService,
              public dialogRef: MatDialogRef<PaymentDialog>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  onClickExit(): void {
    this.dialogRef.close();
  }

  onPaymentSuccessfull(success: boolean): void {
    if (!success) {
      this.toast.error('There was an error while paying for this ticket.');
      return;
    }

    this.toast.success('Payment successfull');
    this.dialogRef.close();
  }
}