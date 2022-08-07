import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadImageService {
  
  private url = "http://localhost:8084/api/v1/renting/properties";
  constructor(private httpClient: HttpClient) { }

  upload(file: File, id:number) : Observable<HttpEvent<any>> {
      const formData: FormData = new FormData();
      formData.append("image", file);
      const request = new HttpRequest('POST', `${this.url}/${id}/image`, formData, {
        responseType: 'json'
      });
      return this.httpClient.request(request);
  }

  getFiles():Observable<any> {
    return this.httpClient.get(`${this.url}/files`);
  }


}
