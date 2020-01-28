import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {API_LOCATION_DAILY_REPORT, API_LOCATION_MONTHLY_REPORT} from "../config/api-paths";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LocationReportService {

  constructor(private http: HttpClient) { }

  getDailyReport(pickedDate: string, eventId: number): Observable<any> {
    return this.http.post(API_LOCATION_DAILY_REPORT + eventId, pickedDate);
  }

  getMonthlyReport(pickedDate: string, eventId: number): Observable<any> {
    return this.http.post(API_LOCATION_MONTHLY_REPORT + eventId, pickedDate);
  }
}
