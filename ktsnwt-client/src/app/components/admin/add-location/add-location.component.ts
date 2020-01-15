import { Component, OnInit, ViewChild, ElementRef, NgZone } from '@angular/core';
import { MapsAPILoader, MouseEvent } from '@agm/core';
import PlaceResult = google.maps.places.PlaceResult;
import { Appearance, Location } from '@angular-material-extensions/google-maps-autocomplete';
import { Title } from '@angular/platform-browser';
import { LocationService } from 'src/app/services/location.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-location',
  templateUrl: './add-location.component.html',
  styleUrls: ['./add-location.component.css']
})
export class AddLocationComponent implements OnInit {

  public appearance = Appearance;
  public zoom: number;
  public latitude: number;
  public longitude: number;
  public selectedAddress: PlaceResult;

  private addressObject;

  constructor(private titleService: Title,
              private locationService: LocationService,
              private toastr: ToastrService) {
  }

  @ViewChild('locationName', {static: false})
  inputName: ElementRef;

  ngOnInit() {
    this.titleService.setTitle('Home | @angular-material-extensions/google-maps-autocomplete');

    this.zoom = 10;
    this.latitude = 52.520008;
    this.longitude = 13.404954;

    this.setCurrentPosition();

  }

  private setCurrentPosition() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = 12;
      });
    }
  }

  onAddLocation() {
    const data = {
      name: this.inputName.nativeElement.value,
      address: this.addressObject
    };

    this.locationService.addLocation(data).subscribe(
      result => {
        console.log(result),
        this.toastr.success('Location successfully added.');
      },
      error => {
        console.log(error);
        this.toastr.error('Location with that name already exists.');
      }
    );
  }

  onAutocompleteSelected(result: PlaceResult) {
    this.addressObject = {
      googleApiId: result.place_id,
      latitude: result.geometry.location.lat(),
      longitude: result.geometry.location.lng()
    };

    result.address_components.forEach(component => {
      switch (component.types[0]) {
        case 'locality':
          this.addressObject.city = component.long_name;
          break;
        case 'country':
          this.addressObject.country = component.long_name;
          break;
        case 'postal_code':
          this.addressObject.postalCode = component.long_name;
          break;
        case 'street_number':
          this.addressObject.streetNumber = component.long_name;
          break;
        case 'route':
          this.addressObject.streetName = component.long_name;
          break;
        default:
      }
    });

    console.log(this.addressObject);
  }

  onLocationSelected(location: Location) {
    this.latitude = location.latitude;
    this.longitude = location.longitude;
  }

}
