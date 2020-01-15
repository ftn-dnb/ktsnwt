import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LocationService } from 'src/app/services/location.service';
import { ToastrService } from 'ngx-toastr';
import { Title } from '@angular/platform-browser';
import PlaceResult = google.maps.places.PlaceResult;
import { Appearance, Location } from '@angular-material-extensions/google-maps-autocomplete';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-location',
  templateUrl: './edit-location.component.html',
  styleUrls: ['./edit-location.component.css']
})
export class EditLocationComponent implements OnInit {

  location: any;

  public appearance = Appearance;
  public zoom: number;
  public latitude: number;
  public longitude: number;
  public selectedAddress: PlaceResult;

  private addressObject;
  private addressContent;
  private locationSelected = false;

  formControl = new FormControl('', [
    Validators.required
  ]);

  @ViewChild('locationName', {static: false})
  inputName: ElementRef;

  constructor(private route: ActivatedRoute,
              private locationService: LocationService,
              private titleService: Title,
              private toastr: ToastrService) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.locationService.getLocationById(id).subscribe(
      (data) => {
        this.location = data;
        this.setCurrentPosition();
      },
      (error) => { console.log(error); }
    );
  }

  private setCurrentPosition() {
    this.zoom = 10;
    this.latitude = this.location.address.latitude;
    this.longitude = this.location.address.longitude;
  }

  onEditLocation() {
    if (this.locationSelected) {
      this.locationService.changeAddress(this.addressObject, this.location.id).subscribe(
        (data) => { this.location = data; },
        (error) => { console.log(error); }
      );
    }

    if (this.inputName.nativeElement.value) {
      const locationData = {
        id: this.location.id,
        name: this.inputName.nativeElement.value
      };
      this.locationService.editLocation(locationData).subscribe(
        (data) => { this.location = data; },
        (error) => { console.log(error); }
      );
    }

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
    this.locationSelected = true;
  }

}
