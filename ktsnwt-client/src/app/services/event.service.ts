import { API_EVENTS, API_EVENTS_ADD, API_EVENTS_IMAGE, API_EVENTS_GET_ONE_BY_ID, API_EVENT_SET_PRICING } from './../config/api-paths';
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

  editEventImage(image: FormData, id: number): Observable<any> {
    return this.http.post(API_EVENTS_IMAGE + id, image);
  }

  getEventById(id: number): Observable<any> {
    return this.http.get(API_EVENTS_GET_ONE_BY_ID + id);
  }

  setEventPricing(id: number, pricing: any): Observable<any> {
    return this.http.put(API_EVENT_SET_PRICING + id, pricing);
  }
}
