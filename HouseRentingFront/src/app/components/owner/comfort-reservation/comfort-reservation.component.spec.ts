import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComfortReservationComponent } from './comfort-reservation.component';

describe('ComfortReservationComponent', () => {
  let component: ComfortReservationComponent;
  let fixture: ComponentFixture<ComfortReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComfortReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComfortReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
