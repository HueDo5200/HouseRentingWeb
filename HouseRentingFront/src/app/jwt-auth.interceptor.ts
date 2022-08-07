import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { SessionService } from './services/session.service';

@Injectable()
export class JwtAuthInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const jwtToken = this.sessionService.getToken();
    if (jwtToken) {
      request = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${jwtToken}`),
      });
    }
    return next.handle(request);
  }
}
