import { Component, OnInit, ViewChild, ElementRef, NgZone } from '@angular/core';
import { MapsAPILoader, MouseEvent } from '@agm/core';
import PlaceResult = google.maps.places.PlaceResult;
import { Appearance, Location } from '@angular-material-extensions/google-maps-autocomplete';
import { Title } from '@angular/platform-browser';
import { LocationService } from 'src/app/services/location.service';
import { ToastrService } from 'ngx-toastr';
import { FormControl, Validators } from '@angular/forms';

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
  private addressContent;

  formControl = new FormControl('', [
    Validators.required
  ]);

  constructor(private titleService: Title,
              private locationService: LocationService,
              private toastr: ToastrService) {
  }

  @ViewChild('locationName', {static: false})
  inputName: ElementRef;

  @ViewChild('autocompleteInput', {static: false})
  autocompleteInput: ElementRef;

  ngOnInit() {
    this.titleService.setTitle('Home | @angular-material-extensions/google-maps-autocomplete');
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
    if (this.formControl.hasError('required') || !this.addressContent) {
      this.toastr.error('Location must have a name.');
      return;
    }
    const data = {
      name: this.inputName.nativeElement.value,
      address: this.addressObject
    };

    this.locationService.addLocation(data).subscribe(
      result => {
        this.toastr.success('Location successfully added.');
      },
      error => {
        this.toastr.error('Location with that name already exists.');
      }
    );
  }

  onChange(result: PlaceResult) {
    this.addressContent = result;
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
  }

  onLocationSelected(location: Location) {
    this.latitude = location.latitude;
    this.longitude = location.longitude;
  }

}
