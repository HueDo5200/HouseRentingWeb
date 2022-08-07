import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

const API = 'http://localhost:8084/api/v1/renting/properties';
const PUBLIC_API = 'http://localhost:8084/api/v1/renting/public/properties'
@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  public searchWord = new BehaviorSubject('');
  public filterItem = new BehaviorSubject('');
  public propertyType = new BehaviorSubject('');
  public openAddPopup = new BehaviorSubject(false);
  public openEditPopup = new BehaviorSubject(false);
  public newProperty = new BehaviorSubject({});
  public updatedProperty = new BehaviorSubject({});
  public getAvailable = new BehaviorSubject(false);
  public place = new BehaviorSubject('');
  public checkInDate = new BehaviorSubject(new Date());
  public checkoutDate = new BehaviorSubject(new Date());
  public filterDate = new BehaviorSubject({"checkInDate": new Date(), "checkoutDate": new Date()})
  public criteria = new BehaviorSubject({"checkinDate": "", "checkoutDate" : "", place: 'Location', type: 'Type', amenities: Array<number>()})

  constructor(private httpClient: HttpClient) { }

  getAllProperties():Observable<any> {

    return this.httpClient.get(PUBLIC_API);
  }

  getAvaialbeProperties(id:number):Observable<any> {
    return this.httpClient.get(`${API}/available/customer/${id}`)
  }

  getCustomerProperties(id:number):Observable<any> {
    return this.httpClient.get(`${API}/customer/${id}`);
  }

  searchProperty(keyword: string):Observable<any> {
    return this.httpClient.get(`${PUBLIC_API}/search?keyword=${keyword}`)
  }

  findById(id:number, customerId: number):Observable<any> {
    return this.httpClient.get(`${PUBLIC_API}/${id}/customer/${customerId}`);
  }

  getImagesOfProperty(id:number):Observable<any> {
    return this.httpClient.get(`${API}/${id}/images`);
  }

  deleteImageOfProperty(id:number):Observable<any> {
    return this.httpClient.delete(`${API}/image/${id}`);
  }

}
