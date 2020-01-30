import { API_BUY_TICKET, API_CANCEL_TICKET, API_MY_RESERVATIONS } from './../config/api-paths';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) { 
  }

  buyTicket(id: number): Observable<any> {
    return this.http.get(`${API_BUY_TICKET}/${id}`);
  }

  cancelTicket(id: number): Observable<any> {
    return this.http.delete(`${API_CANCEL_TICKET}/${id}`);
  }

  getReservationsOnePage(pageNum: number, pageSize: number): Observable<any>  {
    return this.http.get(API_MY_RESERVATIONS, {
      params: new HttpParams()
        .set('page', pageNum.toString())
        .set('size', pageSize.toString())
    });
  }
}
