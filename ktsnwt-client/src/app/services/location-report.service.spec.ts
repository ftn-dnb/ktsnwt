import { TestBed } from '@angular/core/testing';

import { LocationReportService } from './location-report.service';

describe('LocationReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LocationReportService = TestBed.get(LocationReportService);
    expect(service).toBeTruthy();
  });
});
