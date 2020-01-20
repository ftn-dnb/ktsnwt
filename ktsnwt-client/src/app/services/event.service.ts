import { API_EVENTS, API_EVENTS_ADD } from './../config/api-paths';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { EventInfo } from '../models/event-info';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient) {
  }

  getEventsOnePage(pageNum: number, pageSize: number): Observable<any> {
    return this.http.get(API_EVENTS, {
      params: new HttpParams()
              .set('page', pageNum.toString())
              .set('size', pageSize.toString())
    });
  }

  addNewEvent(eventInfo: EventInfo): Observable<any> {
    return this.http.post(API_EVENTS_ADD, eventInfo);
  }
}
