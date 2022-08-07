import { Injectable } from '@angular/core';
import { CustomerService } from './customer.service';


@Injectable({
  providedIn: 'root'
})
export class HeaderServiceService {

  constructor(private customerService: CustomerService) { 

  }

  updateCustomerHeader(): void {
    this.customerService.loggedIn.next(true);
    this.customerService.show.next(false);
  }
  updateOwnerHeader(): void {
    this.customerService.show.next(true);
    this.customerService.isAdmin.next(true);
  }

}
