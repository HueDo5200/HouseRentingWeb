import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer.service';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-reservation-history',
  templateUrl: './reservation-history.component.html',
  styleUrls: ['./reservation-history.component.css']
})
export class ReservationHistoryComponent implements OnInit {
  reservations: any;
  cusId:number = 0;
  total:number = 0;
  constructor(private customerService : CustomerService, 
    private sessionService: SessionService,
    private headerService : HeaderServiceService
    ) { }

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    console.log(user)
    if (user) {
      const userData = JSON.parse(user);
      this.cusId = userData.id;
      this.viewReservationHistory(this.cusId);
      this.headerService.updateCustomerHeader();
    }
  }

  viewReservationHistory(id:number) {
    this.customerService.viewReservationHistory(id).subscribe(res => {
      console.log('history ' + JSON.stringify(res));
      this.reservations = res.data;
      this.calculateTotal(this.reservations);
    })
  }

  calculateTotal(reservations: any) {
    if(reservations && reservations.length > 0) {
      for(let r of reservations) {
        this.total += r.total;
      }
    }
   
  }

}
