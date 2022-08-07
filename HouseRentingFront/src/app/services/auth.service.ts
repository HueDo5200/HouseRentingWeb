import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';
import { JwtHelperService } from '@auth0/angular-jwt';
const AUTH_API = "http://localhost:8084/api/v1/auth";
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private httpClient: HttpClient, private sessionService: SessionService, private jwtService: JwtHelperService) { }

  login(username: string, password: string): Observable<any> {
    return this.httpClient.post(`${AUTH_API}/signin`, {"username": username, "password": password});
  }

  register(user:any):Observable<any> {
    return this.httpClient.post(`${AUTH_API}/register`, user);
  }


  isTokenValid(): any {
    const token = this.sessionService.getToken();
    return token ? !this.jwtService.isTokenExpired(token) : false;
  }

}
