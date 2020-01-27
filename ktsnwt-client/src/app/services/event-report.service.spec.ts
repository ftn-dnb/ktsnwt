import { TestBed } from '@angular/core/testing';

import { EventReportService } from './event-report.service';

describe('EventReportService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventReportService = TestBed.get(EventReportService);
    expect(service).toBeTruthy();
  });
});
