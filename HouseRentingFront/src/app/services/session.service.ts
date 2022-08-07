import { Injectable } from '@angular/core';
const JWT_TOKEN = 'jwt-token';
const USER_KEY = 'user-key';
const HEADER_STATUS = "header-status";
@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  saveToken(token: string): void {
    localStorage.setItem(JWT_TOKEN, token);
  }

  getToken(): string | null {
    return localStorage.getItem(JWT_TOKEN);
  }

  saveUser(user: any): void {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  getUser(): any {
   const item = localStorage.getItem(USER_KEY);
   return item ? item : null; 
  }

  saveHeaderStatus(data:any) {
    localStorage.setItem(HEADER_STATUS, data);
  }

  logout(): void {
    localStorage.clear();
  }
  
}
