import { USER_TOKEN_KEY } from '../config/local-storage-keys';
import { HttpInterceptor, HttpHandler, HttpEvent, HttpRequest, HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";

@Injectable()
export class AddTokenInterceptor implements HttpInterceptor {
  
  constructor(private http: HttpClient){
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let jsonReq: HttpRequest<any> = req.clone({
      setHeaders:{
        Authorization : `Bearer ${localStorage.getItem(USER_TOKEN_KEY)}`
      }
    });
    
    return next.handle(jsonReq);
  }
  
}