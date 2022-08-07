import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationSaleComponent } from './reservation-sale.component';

describe('ReservationSaleComponent', () => {
  let component: ReservationSaleComponent;
  let fixture: ComponentFixture<ReservationSaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReservationSaleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationSaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
