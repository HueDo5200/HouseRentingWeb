import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

const CUSTOMER_API = 'http://localhost:8084/api/v1/renting/customers'
const RESERVATION_API = 'http://localhost:8084/api/v1/renting/reservations'
@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  public loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public show: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  public isAdmin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  constructor(private httpClient: HttpClient) { }

  get isUserLoggedIn() {
    return this.loggedIn.asObservable();
  }

  get isShow() {
    return this.show.asObservable();
  }

  get getIsAdmin() {
    return this.isAdmin.asObservable();
  }

  findById(id:number):Observable<any> {
    return this.httpClient.get(`${CUSTOMER_API}/${id}`);
  }

  reserveProperty(reservation:any):Observable<any> {
    return this.httpClient.post(`${RESERVATION_API}/property`, reservation);
  }

  viewReservationHistory(id:number):Observable<any> {
    return this.httpClient.get(`${RESERVATION_API}/history/customer/${id}`);
  }
  
}
