import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const MESSAGE_API = 'http://localhost:8084/api/v1/chat'
@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private httpClient: HttpClient) { }

 getUserChatInfo(id:number): Observable<any> {
   return this.httpClient.get(`${MESSAGE_API}/user/${id}`);
 }

 getOwnerChatInfo(id:number):Observable<any> {
   return this.httpClient.get(`${MESSAGE_API}/owner/${id}`);
 }

 sendMessage(data:any):Observable<any> {
   return this.httpClient.post(`${MESSAGE_API}/message`, data);
 }

 
 findChatChannelOfUser(id:number, propertyName: string):Observable<any> {
  return this.httpClient.get(`${MESSAGE_API}/find/channel/user/${id}?propertyName=${propertyName}`);
} 
 findChannelById(id:number):Observable<any> {
   return this.httpClient.get(`${MESSAGE_API}/channel/${id}`);
 }

 initializeChannel(data:any):Observable<any> {
   return this.httpClient.post(MESSAGE_API, data);
 }
}
