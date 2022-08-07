import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {
  user:any;
  isCustomer: boolean = false;
  propertyList: any;
  customerList:any;
  page:number = 1;
  count:number = 0;
  pageSize:number = 10;
  // pageSizes = [10, 20, 30]
  sForm:any = {
    number : 10
  }
  pForm:any = {
    size: 10
  }
  amenityName:string = '';
  constructor(private propertyService: PropertyService, 
    private sessionService: SessionService,
    private headerService: HeaderServiceService
    ) { }
 
  ngOnInit(): void {
    const userData = this.sessionService.getUser();
    if (userData) {
      this.user = JSON.parse(userData);
      if(this.user.role.name == 'customer') {
        this.isCustomer = true;
        this.getCustomerProperty(this.user.id);
        this.getAvailableProperties(this.user.id);  
        this.headerService.updateCustomerHeader();
      } else {
        this.getAllProperties();
      }
    } else {
      this.getAllProperties();
    }
   
    this.propertyService.filterItem.subscribe(res => {
      this.amenityName = res;
    })
  }

  getAllProperties(): void {
    this.propertyService.getAllProperties().subscribe(res => {
      this.propertyList = res.data;
      this.count = res.itemsNumber;
    })
  }

  getCustomerProperty(id:number): void {
    this.propertyService.getCustomerProperties(id).subscribe(res => {
      this.customerList = res.data;
     
    })
  } 

  getAvailableProperties(id:number): void {
    this.propertyService.getAvaialbeProperties(id).subscribe(res => {
      this.propertyList = res.data;
    
    })
  }


  onSearchSubmit() {
    const { number } = this.sForm;
    this.propertyList.sort((item1: { avgRating: number; }, item2: {avgRating: number}) => item1.avgRating < item2.avgRating ? 1 : item1.avgRating > item2.avgRating ? -1 : 0)
    this.propertyList = this.propertyList.slice(0, number);
  }

  onSelectPageSize() {
    const {size} = this.pForm;
    if(size > 0 && size <= this.count) {
      this.pageSize = size;
      this.page = 1;
      this.handleGetPropertyList();
    }
  
  }
  compareRating(rating1:number, rating2:number) {
    return rating1 > rating2;
  }


  handlePageChange(event:any) {
    this.page = event;
    this.handleGetPropertyList();
  }

  handleGetPropertyList(): void {
    if(this.user.role.name == 'customer') {
      this.isCustomer = true;
      this.getCustomerProperty(this.user.id);
      this.getAvailableProperties(this.user.id);  
      this.headerService.updateCustomerHeader();
    } else {
      this.getAllProperties();
    }
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.handleGetPropertyList();
  }

}
