import { API_BUY_TICKET } from './../config/api-paths';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
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
}
