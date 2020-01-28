import { API_LOCATIONS_ALL, API_LOCATION_ADD, API_LOCATION_GET_ID, API_LOCATION, API_LOCATION_ADDRESS, API_LOCATION_GET_NAME, API_LOCATION_GET_NAMES, API_HALL_GET, API_SECTOR } from './../config/api-paths';
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
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  getLocationById(locationId): Observable<any> {
    return this.http.get(API_LOCATION_GET_ID + '/' + locationId).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  getLocationByName(locationName: string): Observable<any> {
    return this.http.get(API_LOCATION_GET_NAME + locationName);
  }

  getAllLocationNames(): Observable<any>{
    return this.http.get(API_LOCATION_GET_NAMES);
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

  ////////////////////////////////////////////////////////////////////

  getHallById(hallId): Observable<any> {
    return this.http.get(API_HALL_GET + '/' + hallId).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  addHall(hallData): Observable<any> {
    return this.http.post(API_HALL_GET, hallData).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  addSector(sectorData): Observable<any> {
    return this.http.post(API_SECTOR, sectorData).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  modifySector(sectorData): Observable<any> {
    return this.http.put(API_SECTOR, sectorData).pipe(
      tap(data => console.log(data)),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    const errorMessage = `Server returned code ${err.status}, error message is: ${err.message}`;
    return throwError(errorMessage);
  }
}
