import {Component, OnInit, ViewEncapsulation, ChangeDetectorRef} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs';
import { EventService } from 'src/app/services/event.service';
import { TicketService } from 'src/app/services/ticket.service';
import { ToastrService } from 'ngx-toastr';
import { D3SeatingChart, ShowBehavior } from 'd3-seating-chart';
import * as d3 from 'd3';
import { MatDialog } from '@angular/material';
import { ReservationDialogComponent } from './dialog/reservation-dialog/reservation-dialog.component';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {

  routeSub: Subscription;
  eventDayId: number;
  event: any;
  reservedTickets: any;
  seatsData: any;
  d3sc: any;


  constructor(private route: ActivatedRoute,
              private eventService: EventService,
              private ticketService: TicketService,
              private toastrService: ToastrService,
              private cd: ChangeDetectorRef,
              private router: Router,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.d3sc = D3SeatingChart.attach(document.getElementById('x'), {
      showBehavior: ShowBehavior.DirectDecendants,
      allowManualSelection: true
    });

    this.routeSub = this.route.params.subscribe( params => {
      this.getPathData(params.eventId as number, params.eventDayId as number);
    });
  }

  openDialog(sector) {
    let ticketsReserved = 0;
    let ticketsRemaining;
    this.reservedTickets.forEach(ticket => {
      if (ticket.sectorName === sector.name) {
        ticketsReserved += 1;
      }
    });
    ticketsRemaining = sector.capacity - ticketsReserved;
    if (ticketsRemaining === 0) {
      this.toastrService.error('There is no available seats.');
      return;
    }
    const dialogRef = this.dialog.open(ReservationDialogComponent, {data: {eventInfo: this.event, remaining: ticketsRemaining}});
    dialogRef.afterClosed().subscribe(result => {
      this.onTopViewClick();
      if (result) {
        this.reserveFloorTickets(Number(result), sector.name);
      }
    });
  }


  private getPathData(eventId: number, eventDayId: number): void {
    this.eventDayId = eventDayId;
    this.eventService.getEventById(eventId).subscribe(
      (data) => {
        this.event = data;
        this.ticketService.getTicketsByEventDayId(this.eventDayId).subscribe(
          (dataTickets) => {
            this.reservedTickets = dataTickets;
            this.updateSeatsData();
          },
          (error) => {
            this.toastrService.error(error);
          }
        );
      },
      (error) => {
        this.toastrService.error(error);
      }
    );
  }

  reserveFloorTickets(count, sectorName) {
    const reservationData = {
      eventDayId: this.eventDayId,
      seats: []
    };
    let i;
    for (i = 0; i < count; i++) {
      if (sectorName.startsWith('left')) {
        reservationData.seats.push({
          pricingId: this.event.pricing[0].id
        });
      } else if (sectorName.startsWith('main')) {
        reservationData.seats.push({
          pricingId: this.event.pricing[1].id
        });
      } else {
        reservationData.seats.push({
          pricingId: this.event.pricing[2].id
        });
      }
    }
    this.ticketService.reserveTicket(reservationData).subscribe(
      (data) => {
        this.ticketService.getTicketsByEventDayId(this.eventDayId).subscribe(
          (dataTickets) => {
            this.reservedTickets = dataTickets;
            this.updateSeatsData();
          },
          (error) => {
            this.toastrService.error(error);
          }
        );
      },
      (error) => {
        this.toastrService.error(error);
      }
    );
  }

  onSectorClick(e) {
    let seatType;
    let selectedSector;
    const sectorName = e.target.attributes['zoom-control'].value;
    this.event.hall.sectors.forEach((sector) => {
      if (sector.name === sectorName) {
        seatType = sector.type;
        selectedSector = sector;
      }
    });
    if (seatType === 'FLOOR') {
      this.openDialog(selectedSector);
    }
  }

  updateSeatsData() {
    let seatsSector = {};
    const ticketData = [];
    this.reservedTickets.forEach(ticket => {
      ticketData.push({row: ticket.row, column: ticket.seat, sector: ticket.sectorName});
    });
    this.event.hall.sectors.forEach(sector => {
      switch (sector.name) {
        case 'left':
          seatsSector = {...seatsSector, ...{left : {rows: sector.numRows, cols: sector.numColumns, type: sector.type}}};
          break;
        case 'main':
          seatsSector = {...seatsSector, ...{main : {rows: sector.numRows, cols: sector.numColumns, type: sector.type}}};
          break;
        case 'right':
          seatsSector = {...seatsSector, ...{right : {rows: sector.numRows, cols: sector.numColumns, type: sector.type}}};
          break;
      }
    });
    seatsSector = {...seatsSector, ...{d3sc: this.d3sc}};

    this.seatsData = {
      seats: seatsSector,
      tickets: ticketData
    };
    this.cd.detectChanges();
  }

  getPricingIdBySectorName(name) {
    let sectorId;
    for (let sector of this.event.hall.sectors) {
      if (name === sector.name) {
        sectorId = sector.id;
        for (let pricing of this.event.pricing) {
          if (pricing.sectorId === sectorId) {
            return pricing.id;
          }
        }
      }
    }
  }

  onReserveTicket() {
    const selected = d3.selectAll('rect[selected]').nodes();
    d3.selectAll('rect[selected]').attr('selected', null);
    if (this.event.ticketsPerUser < selected.length) {
      this.toastrService.error('Too many seats selected.');
      this.cd.detectChanges();
      return;
    }
    if (selected.length <= 0) {
      this.toastrService.error('Invalid number of tickets chosen.');
      return;
    }
    const reservationData = {
      eventDayId: this.eventDayId,
      seats: []
    };
    selected.forEach((node: any) => {
      const className: string = node.parentNode.className.baseVal;

      const rowNum = Number(node.getAttribute('row'));
      const column = Number(node.getAttribute('column'));

      let pricing;

      if (className.startsWith('left')) {
        pricing = this.getPricingIdBySectorName('left');
        reservationData.seats.push({
          row: rowNum,
          seat: column,
          pricingId: pricing
        });
      } else if (className.startsWith('main')) {
        pricing = this.getPricingIdBySectorName('main');
        reservationData.seats.push({
          row: rowNum,
          seat: column,
          pricingId: pricing
        });
      } else {
        pricing = this.getPricingIdBySectorName('right');
        reservationData.seats.push({
          row: rowNum,
          seat: column,
          pricingId: pricing
        });
      }
    });

    this.ticketService.reserveTicket(reservationData).subscribe(
      (data) => {
        this.ticketService.getTicketsByEventDayId(this.eventDayId).subscribe(
          (dataTickets) => {
            this.reservedTickets = dataTickets;
            this.updateSeatsData();
          },
          (error) => {
            this.toastrService.error(error);
          }
        );
      },
      (error) => {
        this.toastrService.error(error);
      }
    );
  }

  onTopViewClick() {
    this.d3sc.goToBoard();
  }
}
