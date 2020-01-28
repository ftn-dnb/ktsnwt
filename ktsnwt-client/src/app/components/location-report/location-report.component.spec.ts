import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LocationReportComponent } from './location-report.component';

describe('LocationReportComponent', () => {
  let component: LocationReportComponent;
  let fixture: ComponentFixture<LocationReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
