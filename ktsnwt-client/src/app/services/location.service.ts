import { API_LOCATIONS_ALL } from './../config/api-paths';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) { 
  }

  getNumberOfLocations() {
    // TODO: implement this
    return 10;
  }

  getLocationsOnePage(pageNum: number, pageSize: number): Observable<any> {
    return this.http.get(API_LOCATIONS_ALL, {
      params: new HttpParams()
              .set('page', pageNum.toString())
              .set('size', pageSize.toString())
    });
  }
}
