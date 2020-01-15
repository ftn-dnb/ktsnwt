import { TestBed } from '@angular/core/testing';

import { MyReservationsService } from './my-reservations.service';

describe('MyReservationsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MyReservationsService = TestBed.get(MyReservationsService);
    expect(service).toBeTruthy();
  });
});
