import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FilterService } from 'src/app/services/filter.service';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  amenities: any;
  types: any;
  selected: number = 1;
  current: Date = new Date();
  amenityIds: Array<number> = [];
  amenitySelected: Array<Boolean> = [];
  form: any = {
    checkinDate: new Date(),
    checkoutDate: new Date(),
    place: '',
    type: 'Type',
    amenity: 'Amenity',
  };
  invalidDate: boolean = false;
  constructor(
    private filterService: FilterService,
    private propertyService: PropertyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.findAllAmenity();
    this.findAllPropertyTypes();
  }

  findAllAmenity(): void {
    this.filterService.findAllAmenities().subscribe((res) => {
      this.amenities = res.data;
    });
  }

  findAllPropertyTypes(): void {
    this.filterService.findAllPropertyType().subscribe((res) => {
      this.types = res.data;
    });
  }

  selectOption(event: any) {
    if (event.target.value != 'Type') {
      this.propertyService.propertyType.next(event.target.value);
      this.router.navigate(['/search-result']);
    }
  }

  selectAmenity(event: any) {
    if (event.target.value != 'Amenity') {
      this.propertyService.filterItem.next(event.target.value);
      this.router.navigate(['/search-result']);
    }
  }

  selectAmenityById(id: number, i: number): void {
    if(!this.amenitySelected[i]) {
      this.amenitySelected[i] = true;
      this.amenityIds.push(id);
    } else {
      this.amenitySelected[i] = false;
      this.amenityIds.splice(i, 1);
    }
   
  
  }

  onSubmit() {
    const { checkinDate, checkoutDate, place, amenity, type } = this.form;
    let inDate = new Date();
    let outDate = new Date();
    if (checkinDate != '' && checkoutDate != '') {
      inDate = new Date(checkinDate);
      outDate = new Date(checkoutDate);
    }
    let location = '';
    if (place == '') {
      location = 'Location';
    } else {
      location = place;
    }
    if (inDate.getTime() > outDate.getTime()) {
      alert('Invalid date! Checkout date must be after checkin date');
    } else {
      // this.propertyService.getAvailable.next(true);
      // this.propertyService.checkInDate.next(inDate);
      // this.propertyService.checkoutDate.next(outDate);
      // this.propertyService.filterDate.next({"checkInDate": inDate, "checkoutDate": outDate});

      this.propertyService.criteria.next({
        checkinDate: inDate.toISOString().replace('T', ' ').split('.')[0],
        checkoutDate: outDate.toISOString().replace('T', ' ').split('.')[0],
        amenities: this.amenityIds,
        type: type,
        place: location,
      });
      if(this.amenityIds.length > 0) {
        this.amenityIds = [];
        this.amenitySelected.fill(false);

      }
      this.router.navigate(['/search-result']);

      // let strInDate = this.toLocalDate(inDate);
      // let strOutDate = this.toLocalDate(outDate);
      // this.propertyService.criteria.next({"checkinDate" : strInDate.substring(0, 10), "checkoutDate": strOutDate.substring(0, 10), "amenity" : amenity, "type" : type, "place": place});
      // this.router.navigate(['/search-result']);
    }
  }

  toLocalDate(d: Date): string {
    // Create date at UMT-0
    var date = new Date();
    // Modify the UMT + 2 hours
    date.setHours(date.getHours() + 7);
    // Reformat the timestamp without the "T", as YYYY-MM-DD hh:mm:ss
    return date.toISOString().replace('T', ' ').split('.')[0];
  }
}
