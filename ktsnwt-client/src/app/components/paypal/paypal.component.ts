import { TicketService } from './../../services/ticket.service';
import { TicketBuyingData } from '../../models/request/ticket-buying-data';
import { Component, OnInit, ViewChild, ElementRef, Input, Output, EventEmitter } from '@angular/core';

declare var paypal;

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
})
export class PaypalComponent implements OnInit {

  @ViewChild('paypal', { static: true })
  paypalElement: ElementRef;

  @Input()
  ticketData: TicketBuyingData;

  @Output()
  paymentSuccessfull = new EventEmitter<boolean>();

  constructor(private ticketService: TicketService) {
  }

  ngOnInit() {
    this.setupPaypal();
  }

  private setupPaypal(): void {
    paypal
    .Buttons({
      createOrder: (data, actions) => {
        return actions.order.create({
          purchase_units: [
            {
              description: `Buying ticket for date: ${this.ticketData.date}`,
              amount: {
                currency_code: 'USD',
                value: this.ticketData.price
              }
            }
          ]
        });
      },

      onApprove: async (data, actions) => {
        const order = await actions.order.capture();

        this.ticketService.buyTicket(this.ticketData.ticketId).subscribe(data => {
          this.paymentSuccessfull.emit(true);
        }, error => {
          this.paymentSuccessfull.emit(false);
        });
      },

      onError: err => {
        this.paymentSuccessfull.emit(false);
      }
    })
    .render(this.paypalElement.nativeElement);
  }
}
