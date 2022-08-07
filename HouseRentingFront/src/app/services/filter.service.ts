import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
const CUSTOMER_API = 'http://localhost:8084/api/v1/renting/customers';
const AMENITY_API = 'http://localhost:8084/api/v1/renting/amenities';
const PROPERTY_API = 'http://localhost:8084/api/v1/renting/properties';
const PUBLIC_PROPERTY_API = 'http://localhost:8084/api/v1/renting/public/properties';
const PROPERTY_TYPE_API = 'http://localhost:8084/api/v1/renting/property-types';
@Injectable({
  providedIn: 'root'
})
export class FilterService{

  constructor(private httpClient: HttpClient) { }


  findAllAmenities():Observable<any> {
      return this.httpClient.get(AMENITY_API);
  }

  findAllPropertyType():Observable<any> {
    return this.httpClient.get(PROPERTY_TYPE_API);
  }

  findByPropertyType(name:string):Observable<any> {
    return this.httpClient.get(`${CUSTOMER_API}/find/property?name=${name}`);
  }

  findByAmenityType(name:string):Observable<any> {
    return this.httpClient.get(`${CUSTOMER_API}/find/property/amenity?name=${name}`);
  }

  findAvailableProperty(res:any):Observable<any> {
    return this.httpClient.post(`${CUSTOMER_API}/find/all/available`, res);
  }

  findPropertyByPlace(place:string):Observable<any> {
    return this.httpClient.get(`${CUSTOMER_API}/find/property/place?place=${place}`);
  }

  searchProperty(res:any):Observable<any> {
    return this.httpClient.get(`${PUBLIC_PROPERTY_API}/criteria?place=${res.place}&type=${res.type}&amenities=${res.amenities}&checkInDate=${res.checkinDate}&checkoutDate=${res.checkoutDate}`)
  }
}
