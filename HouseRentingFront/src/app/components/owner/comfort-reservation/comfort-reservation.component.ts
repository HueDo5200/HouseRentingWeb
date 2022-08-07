import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer.service';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { OwnerService } from 'src/app/services/owner.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-comfort-reservation',
  templateUrl: './comfort-reservation.component.html',
  styleUrls: ['./comfort-reservation.component.css'],
})
export class ComfortReservationComponent implements OnInit {
  id: number = 0;
  reservations: any;
  customer: any;
  index:number = 0;
  constructor(
    private ownerService: OwnerService,
    private customerService: CustomerService,
    private sessionService: SessionService,
    private headerService: HeaderServiceService
  ) {}

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (user) {
      const userObject = JSON.parse(user);
      this.id = userObject.id;
      this.getReservationOfOwner(this.id);
      this.headerService.updateOwnerHeader();
    }
  }

  getReservationOfOwner(id: number) {
    this.ownerService.viewReservation(id).subscribe((res) => {
      if (res.data) {
        console.log(' res ' + JSON.stringify(res));
        this.reservations = res.data;
      }
    });
  }

  showCustomerInfo(id: number) {
    this.customerService.findById(id).subscribe((res) => {
      this.index = res.data.id;
      this.customer = res.data;
    });
  }

  comformTo(reservationId: number, status: number) {
    this.ownerService
      .updateResevationStatus(status, reservationId)
      .subscribe((res) => {
        const index = this.reservations.findIndex(
          (item: { id: number }) => item.id == reservationId
        );
        if(index != -1) {
          this.reservations[index].status = status;
        }
      });
  }

  onSearchSubmit(event: any) {
    let word: string = event.target.value;
    if (word !== '') {
      this.filterReservation(word);
    } else {
      this.getReservationOfOwner(this.id);
    }
  }

  filterReservation(searchWord: string): void {
    this.reservations = this.reservations.filter(
      (property: {
        id: number;
        propertyName: string;
      
      }) =>
        property.propertyName
          .split(' ')
          .join('')
          .toLowerCase()
          .includes(searchWord.split(' ').join('').toLowerCase()) ||
        searchWord.includes(property.propertyName)
    );
  }
}
