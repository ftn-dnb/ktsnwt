import { API_BUY_TICKET, API_CANCEL_TICKET, API_MY_RESERVATIONS, API_GET_TICKETS_BY_EVENT_DAY, API_RESERVE_TICKETS } from './../config/api-paths';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs/operators';

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

  getTicketsByEventDayId(eventDayId: number) {
    return this.http.get(API_GET_TICKETS_BY_EVENT_DAY + eventDayId).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  reserveTicket(reservationData: any): Observable<any> {
    return this.http.post(API_RESERVE_TICKETS, reservationData).pipe(
      tap((data) => console.log(data)),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    const errorMessage = `Server returned code ${err.status}, error message is: ${err.message}`;
    return throwError(errorMessage);
  }
}
