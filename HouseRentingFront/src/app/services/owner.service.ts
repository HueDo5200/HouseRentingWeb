import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const OWNER_API = 'http://localhost:8084/api/v1/renting/properties'

const PROPERTY_API = 'http://localhost:8084/api/v1/renting/properties';
const RESERVATION_API = 'http://localhost:8084/api/v1/renting/reservations';
@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  constructor(private httpClient: HttpClient) { }

  findAllProperty(id:number): Observable<any> {
    return this.httpClient.get(`${PROPERTY_API}/owner/${id}`);
  }

  addNewProperty(property: any):Observable<any> {
    return this.httpClient.post(PROPERTY_API, property);
  }

  deleteProperty(id:number):Observable<any> {
    return this.httpClient.delete(`${OWNER_API}/${id}`);
  }

  updateProperty(property: any, id:number):Observable<any> {
    return this.httpClient.put(`${OWNER_API}/${id}`, property);
  }

  uploadImage(data: any, propertyId: number): Observable<any> {
    return this.httpClient.post(`${PROPERTY_API}/${propertyId}/image`, data);
  }

  viewReservation(id:number): Observable<any> {
    return this.httpClient.get(`${RESERVATION_API}/owner/${id}`);
  }

  viewReservationSale(id:number):Observable<any> {
    return this.httpClient.get(`${RESERVATION_API}/sale/owner/${id}`);
  }

  updateResevationStatus(status:number, reservationId:number):Observable<any> {
    return this.httpClient.put(`${RESERVATION_API}/status`, {"status" : status, "reservationId": reservationId});
  }
}
