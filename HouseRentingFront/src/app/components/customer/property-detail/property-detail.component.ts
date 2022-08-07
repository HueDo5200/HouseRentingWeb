
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from 'src/app/services/customer.service';
import { MessageService } from 'src/app/services/message.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';


@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.css'],
})
export class PropertyDetailComponent implements OnInit {
  user: any;
  property: any;
  isCustomer: boolean = false;
  invalidDate: boolean = false;
  requireLogin:boolean = false;
  current: Date = new Date();
  id:number = 0;
  customerId:number = 0;
  form: any = {
   checkinDate: new Date(),
   checkoutDate: new Date(),
  }

  constructor(
    private route: ActivatedRoute,
    private propertyService: PropertyService,
    private sessionService: SessionService,
    private customerService: CustomerService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    const userData = this.sessionService.getUser();
    if (userData) {
      this.user = JSON.parse(userData);
      if(this.user.role.name == 'customer') {
        this.isCustomer = true;
        this.customerId = this.user.id;
      }
     
    } 
   
    this.id = this.route.snapshot.params['id'];
    this.propertyService.findById(this.id, this.customerId).subscribe((res) => {
      this.property = res.data;
    });
  }

  reserveProperty(checkinDate: Date, checkoutDate: Date) {
      const days = (checkoutDate.getTime() - checkinDate.getTime()) / (1000 * 3600 * 24);
      const reservation = {
        "userId": this.user.id,
        "propertyId": this.id,
        "startDate": checkinDate,
        "endDate": checkoutDate,
        "total": days * this.property.pricePerNight,
        "email": this.property.ownerEmail
      }
      this.customerService.reserveProperty(reservation).subscribe(res => {
       console.log("data " + JSON.stringify(res));
        if(res.data) {
          alert("Reserve successfully!");
          this.property.reservationStatus = 0;
          this.property.total = reservation.total;
          this.property.startDate = reservation.startDate;
          this.property.endDate = reservation.endDate;
        }
      }, error => {
       
        alert("Property is reserved your selected time. Please select other period of time!");
      })
  }

  onSubmit() {
    if(!this.isCustomer) {
        this.requireLogin = true;
        return;
    }
   const {checkinDate, checkoutDate} = this.form;
   console.log('form ' + JSON.stringify(this.form));
   const inDate = new Date(checkinDate);
   const outDate = new Date(checkoutDate);
  
   if(inDate.getDate() < new Date().getDate() || outDate.getDate() < new Date().getDate() || inDate.getTime() > outDate.getTime()) {
    this.invalidDate = true;
   } else {
    this.reserveProperty(inDate, outDate);
    const chatChannel =  {
      "userId": this.user.id,
      "username": this.user.username,
      "dateCreated": new Date(),
      "propertyName": this.property.name,
      "ownerId": this.property.ownerId,
      "ownerName": this.property.ownerName
    }
    this.messageService.initializeChannel(chatChannel).subscribe(res => {
     
    })
   }  

  }
}
