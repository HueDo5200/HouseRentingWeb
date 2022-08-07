import { JsonpClientBackend } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FilterService } from 'src/app/services/filter.service';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { PropertyService } from 'src/app/services/property.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css'],
})
export class SearchResultComponent implements OnInit, OnDestroy {
  propertyList: any;
  sForm:any = {
    number : 10
  }
  constructor(
    private propertyService: PropertyService,
    private filterService: FilterService
  ) {}
  ngOnDestroy(): void {
    this.propertyService.searchWord.next('');
    this.propertyService.filterItem.next('');
  }

  ngOnInit(): void {
    this.subscribeKeyword();
  }

  onSearchSubmit() {
    const { number } = this.sForm;
    this.propertyList.sort((item1: { avgRating: number; }, item2: {avgRating: number}) => item1.avgRating < item2.avgRating ? 1 : item1.avgRating > item2.avgRating ? -1 : 0)
    this.propertyList = this.propertyList.slice(0, number);
  }
  
  subscribeKeyword() {
    this.propertyService.searchWord.subscribe((res) => {
      if (res != '') {
        this.propertyService.searchProperty(res).subscribe((response) => {
          this.propertyList = response.data;
        });
      }
    });
    this.propertyService.filterItem.subscribe((res) => {
      if (res != '') {
        this.filterService.findByAmenityType(res).subscribe((response) => {
          this.propertyList = response.data;
        });
      }
    });

    this.propertyService.propertyType.subscribe(res => {
      if(res != '') {
        this.filterService.findByPropertyType(res).subscribe(response => {
              this.propertyList = response.data;
        })
      }
    })

    this.propertyService.getAvailable.subscribe(res => {
      if(res) {
        this.propertyService.filterDate.subscribe(res => {
          this.filterService.findAvailableProperty(res).subscribe(response => {
            this.propertyList = response.data;
          })
        })
       
      }
    })

    this.propertyService.place.subscribe(res => {
      if(res != '') {
        this.filterService.findPropertyByPlace(res).subscribe(response => {
          this.propertyList = response.data;
        })
      }
    })

    this.propertyService.criteria.subscribe(res => {
      if(res) {
        console.log('defined ' + JSON.stringify(res))
        this.filterService.searchProperty(res).subscribe(response => {
          console.log('respose ' + JSON.stringify(response));
          this.propertyList = response.data;
        })
      }
    })
  }
}
