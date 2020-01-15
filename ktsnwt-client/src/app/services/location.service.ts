import { API_LOCATIONS_ALL, API_LOCATION_ADD, API_LOCATION_GET_ID, API_LOCATION, API_LOCATION_ADDRESS } from './../config/api-paths';
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

  getLocationById(locationId): Observable<any> {
    return this.http.get(API_LOCATION_GET_ID + '/' + locationId).pipe(
      tap(data => console.log(JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editLocation(locationData): Observable<any> {
    return this.http.put(API_LOCATION, locationData).pipe(
      tap(data => console.log(JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  changeAddress(addressData, locationId): Observable<any> {
    return this.http.put(API_LOCATION_ADDRESS + '/' + locationId, addressData).pipe(
      tap(data => console.log(JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    const errorMessage = `Server returned code ${err.status}, error message is: ${err.message}`;
    return throwError(errorMessage);
  }
}
