import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer.service';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { OwnerService } from 'src/app/services/owner.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-reservation-sale',
  templateUrl: './reservation-sale.component.html',
  styleUrls: ['./reservation-sale.component.css'],
})
export class ReservationSaleComponent implements OnInit {
  reservations: any;
  ownerId: number = 0;
  cusId: number = 0;
  customer: any;
  total: number = 0;
  constructor(
    private ownerService: OwnerService,
    private sessionService: SessionService,
    private customerService: CustomerService,
    private headerService:HeaderServiceService
  ) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (user) {
      const userData = JSON.parse(user);
      this.ownerId = userData.id;
      this.viewReservationHistory(this.ownerId);
      this.headerService.updateOwnerHeader();
    }
  }

  viewReservationHistory(id: number) {
    this.ownerService.viewReservationSale(id).subscribe((res) => {
      this.reservations = res.data;
      console.log('sale ' + JSON.stringify(res))
      this.calculateTotal(this.reservations);
    });
  }
  
  calculateTotal(reservations: any) {
    if(reservations && reservations.length > 0) {
      for (let reservation of reservations) {
        this.total += reservation.total;
      }
    }
   
  }

  showCustomerInfo(id: number) {
    this.customerService.findById(id).subscribe((res) => {
      this.cusId = res.data.id;
      this.customer = res.data;
    });
  }

  onSearchSubmit(event: any) {
    let word: string = event.target.value;
    if (word !== '') {
      this.filterReservation(word);
    } else {
      this.viewReservationHistory(this.ownerId);
    }
  }

  filterReservation(searchWord: string): void {
    this.reservations = this.reservations.filter(
      (property: { id: number; propertyName: string }) =>
        property.propertyName
          .split(' ')
          .join('')
          .toLowerCase()
          .includes(searchWord.split(' ').join('').toLowerCase()) ||
        searchWord.includes(property.propertyName)
    );
  }
}
