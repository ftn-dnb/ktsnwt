import { TestBed, async, inject } from '@angular/core/testing';

import { ShowEventGuard } from './show-event.guard';

describe('ShowEventGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ShowEventGuard]
    });
  });

  it('should ...', inject([ShowEventGuard], (guard: ShowEventGuard) => {
    expect(guard).toBeTruthy();
  }));
});
