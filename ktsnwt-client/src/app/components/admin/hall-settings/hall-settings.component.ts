import { NOT_FOUND } from './../../../config/router-paths';
import { OnInit, Component, ViewEncapsulation, ViewChild, ElementRef } from '@angular/core';
import { D3SeatingChart, ShowBehavior } from 'd3-seating-chart';
import { ActivatedRoute, Router } from '@angular/router';
import { LocationService } from 'src/app/services/location.service';
import { Validators, FormControl } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-hall-settings',
  encapsulation: ViewEncapsulation.None,
  templateUrl: './hall-settings.component.html',
  styleUrls: ['./hall-settings.component.css']
})
export class HallSettingsComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private locationService: LocationService,
              private toastr: ToastrService) {}

  public d3sc: any;
  seatsData: any;
  apiData: any;

  selectedTypeLeft = 'FLOOR';
  selectedTypeMain = 'FLOOR';
  selectedTypeRight = 'FLOOR';

  @ViewChild('leftRow', {static: false}) leftRow: ElementRef;
  @ViewChild('leftCol', {static: false}) leftCol: ElementRef;
  @ViewChild('mainRow', {static: false}) mainRow: ElementRef;
  @ViewChild('mainCol', {static: false}) mainCol: ElementRef;
  @ViewChild('rightRow', {static: false}) rightRow: ElementRef;
  @ViewChild('rightCol', {static: false}) rightCol: ElementRef;

  ngOnInit(): void {
    this.d3sc = D3SeatingChart.attach(document.getElementById('x'), {
      showBehavior: ShowBehavior.DirectDecendants,
      allowManualSelection: true
    });

    const id = +this.route.snapshot.paramMap.get('id');
    this.locationService.getHallById(id).subscribe(
      (data) => {
        this.apiData = data;
        this.updateSeatsData();
      },
      (error) => {
        this.router.navigate([NOT_FOUND]);
      }
    );
  }

  modifySector(name: string) {
    let row;
    let col;
    let cap;
    let type;
    if (name === 'left') {
      if (!this.leftCol.nativeElement.value || !this.leftRow.nativeElement.value) {
        this.toastrErrMsg('Col or Row values are empty.');
        return;
      }
      if (isNaN(this.leftRow.nativeElement.value) || isNaN(this.leftCol.nativeElement.value)) {
        this.toastrErrMsg('Col or Row values are not numbers.');
        return;
      }
      col = Number(this.leftCol.nativeElement.value);
      row = Number(this.leftRow.nativeElement.value);
      cap = col * row;
      type = this.selectedTypeLeft;
    } else if (name === 'main') {
      if (!this.mainCol.nativeElement.value || !this.mainRow.nativeElement.value) {
        this.toastrErrMsg('Col or Row values are empty.');
        return;
      }
      if (isNaN(this.mainCol.nativeElement.value) || isNaN(this.mainRow.nativeElement.value)) {
        this.toastrErrMsg('Col or Row values are not numbers.');
        return;
      }
      col = Number(this.mainCol.nativeElement.value);
      row = Number(this.mainRow.nativeElement.value);
      cap = col * row;
      type = this.selectedTypeMain;
    } else {
      if (!this.rightCol.nativeElement.value || !this.rightRow.nativeElement.value) {
        this.toastrErrMsg('Col or Row values are empty.');
        return;
      }
      if (isNaN(this.rightCol.nativeElement.value) || isNaN(this.rightRow.nativeElement.value)) {
        this.toastrErrMsg('Col or Row values are not numbers.');
        return;
      }
      col = Number(this.rightCol.nativeElement.value);
      row = Number(this.rightRow.nativeElement.value);
      cap = col * row;
      type = this.selectedTypeRight;
    }

    this.apiData.sectors.forEach(sector => {
      if (sector.name === name) {
        sector.numRows = row;
        sector.numColumns = col;
        sector.capacity = cap;
        sector.type = this.getType(type);
        this.locationService.modifySector(sector).subscribe(
          (dataSec) => {
            this.locationService.getHallById(dataSec.hallId).subscribe(
              (data) => {
                this.apiData = data;
                this.updateSeatsData();
              },
              (error) => {
                console.log(error);
              }
            );
          }
        );
      }
    });
  }

  onTopViewClick() {
    this.d3sc.goToBoard();
  }

  toastrErrMsg(msg: string) {
    this.toastr.error(msg);
  }

  getType(type: string) {
    if (type === 'FLOOR') {
      return 0;
    } else {
      return 1;
    }
  }

  updateSeatsData() {
    let seats = {};
    this.apiData.sectors.forEach(sector => {
      switch (sector.name) {
        case 'left':
          seats = {...seats, ...{left : {rows: sector.numRows, cols: sector.numColumns}}};
          break;
        case 'main':
          seats = {...seats, ...{main : {rows: sector.numRows, cols: sector.numColumns}}};
          break;
        case 'right':
          seats = {...seats, ...{right : {rows: sector.numRows, cols: sector.numColumns}}};
          break;
      }
    });
    seats = {...seats, ...{d3sc: this.d3sc}};

    this.seatsData = seats;
  }

}
