import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {API_MY_RESERVATIONS} from '../config/api-paths';
import {HttpClient, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MyReservationsService {

  constructor(private http: HttpClient) { }


  getReservationsOnePage(pageNum: number, pageSize: number): Observable<any>  {
    return this.http.get(API_MY_RESERVATIONS, {
      params: new HttpParams()
        .set('page', pageNum.toString())
        .set('size', pageSize.toString())
    });
  }
}
