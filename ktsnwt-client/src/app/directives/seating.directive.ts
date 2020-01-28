import { ElementRef, Directive, Input, OnInit, Renderer2, OnChanges, SimpleChanges } from "@angular/core";
import * as d3 from 'd3';
@Directive({
  selector: '[appSeating]'
})
export class SeatingDirective implements OnChanges {

  @Input('appSeating') seatingData: any;

  constructor(private el: ElementRef,
              private renderer: Renderer2) { }

  ngOnChanges(changes: SimpleChanges): void {
    const element = this.el.nativeElement;
    const className = element.className.baseVal;

    console.log(this.seatingData);

    const data = this.seatingData[className];

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

    for (i = 0; i < cols; i++) {
      for (j = 0; j < rows; j++) {
        d3.select(`g.${className}`)
          .append('rect')
            .attr('seat', '')
            .attr('height', seatHeight.toString())
            .attr('width', seatWidth.toString())
            .attr('y', (seatY + biasY * j).toString())
            .attr('x', (seatX + biasX * i).toString())
            .on('click', function() {
              if (d3.select(this).attr('selected') === '') {
                d3.select(this).attr('selected', null);
              } else {
                d3.select(this).attr('selected', '');
              }
            });
      }
    }

    this.seatingData.d3sc.goToBoard();

  }

}
