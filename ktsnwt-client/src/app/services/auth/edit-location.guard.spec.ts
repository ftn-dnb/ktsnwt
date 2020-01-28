import { TestBed, async, inject } from '@angular/core/testing';

import { EditLocationGuard } from './edit-location.guard';

describe('EditLocationGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EditLocationGuard]
    });
  });

  it('should ...', inject([EditLocationGuard], (guard: EditLocationGuard) => {
    expect(guard).toBeTruthy();
  }));
});
