import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {API_EVENT_DAILY_REPORT} from '../config/api-paths';

@Injectable({
  providedIn: 'root'
})
export class EventReportService {

  constructor(private http: HttpClient) { }


  getDailyReport(pickedDate: string, eventId: number): Observable<any>  {
    return this.http.post(API_EVENT_DAILY_REPORT + eventId, pickedDate);
  }




}
