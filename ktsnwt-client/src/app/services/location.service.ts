import { API_LOCATIONS_ALL, API_LOCATION_ADD } from './../config/api-paths';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, catchError } from 'rxjs/operators';

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

  addLocation(locationData): Observable<any> {
    return this.http.post(API_LOCATION_ADD, locationData).pipe(
      tap(data => console.log(JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    const errorMessage = `Server returned code ${err.status}, error message is: ${err.message}`;
    return throwError(errorMessage);
  }
}
