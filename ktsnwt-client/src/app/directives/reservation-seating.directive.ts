import { ElementRef, Directive, Input, OnChanges, SimpleChanges } from "@angular/core";
import * as d3 from 'd3';
@Directive({
  selector: '[appSeatingRes]'
})
export class ReservationSeatingDirective implements OnChanges {

  @Input('appSeatingRes') seatingData: any;

  constructor(private el: ElementRef) { }

  ngOnChanges(changes: SimpleChanges): void {
    const element = this.el.nativeElement;
    const className = element.className.baseVal;

    if (!this.seatingData) {
      return;
    }

    const data = this.seatingData.seats[className];

    const rows = data.rows;
    const cols = data.cols;

    let i: number;
    let j: number;

    const seatHeight = 10.080303;
    const seatWidth = 9;

    const seatY = 100;
    const seatX = 210;

    const biasY = 11;
    const biasX = 10;

    d3.select(`g.${className}`)
      .selectAll('*')
      .remove();

    if (data.type === 'SEATS') {
      for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
          if (this.isSeatReserved(i + 1, j + 1, className)) {
            d3.select(`g.${className}`)
            .append('rect')
              .attr('locked', 'reserved')
              .attr('seat', '')
              .attr('height', seatHeight.toString())
              .attr('width', seatWidth.toString())
              .attr('y', (seatY - biasY * i).toString())
              .attr('x', (seatX + biasX * j).toString())
              .attr('row', i + 1)
              .attr('column', j + 1)
              .on('click', function() {
                if (d3.select(this).attr('locked')) {
                  return;
                }
                if (d3.select(this).attr('selected') === '') {
                  d3.select(this).attr('selected', null);
                } else {
                  d3.select(this).attr('selected', '');
                }
              });
          } else {
            d3.select(`g.${className}`)
            .append('rect')
              .attr('seat', '')
              .attr('height', seatHeight.toString())
              .attr('width', seatWidth.toString())
              .attr('y', (seatY - biasY * i).toString())
              .attr('x', (seatX + biasX * j).toString())
              .attr('row', i + 1)
              .attr('column', j + 1)
              .on('click', function() {
                if (d3.select(this).attr('selected') === '') {
                  d3.select(this).attr('selected', null);
                } else {
                  d3.select(this).attr('selected', '');
                }
              });
          }
        }
      }
    } else {
      d3.select(`g.${className}`)
        .append('rect')
        .attr('floor', '')
        .attr('height', (seatHeight * 10).toString())
        .attr('width', (seatWidth * 10).toString())
        .attr('y', '110')
        .attr('x', '110')
        .on('click', function() {
          if (d3.select(this).attr('selected-floor') === '') {
            d3.select(this).attr('selected-floor', null);
          } else {
            d3.select(this).attr('selected-floor', '');
          }
        });
    }


    this.seatingData.seats.d3sc.goToBoard();

  }

  isSeatReserved(i, j, className) {
    let reserved = false;
    this.seatingData.tickets.forEach((ticket) => {
      if (ticket.row === i && ticket.column === j && ticket.sector === className) {
        reserved = true;
      }
    });
    return reserved;
  }

}
