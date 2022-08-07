import { Component, Input, OnInit } from '@angular/core';
import { FilterService } from 'src/app/services/filter.service';
import { OwnerService } from 'src/app/services/owner.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-update-property',
  templateUrl: './update-property.component.html',
  styleUrls: ['./update-property.component.css'],
})
export class UpdatePropertyComponent implements OnInit {
  @Input() property: any;
  user: any;
  types: any;
  amenities: any;
  form: any = {
    name: '',
    description: '',
    pricePerNight: 0,
    propertyType: {},
    bedroom: 1,
    bathroom: 1,
    kitchen: 1,
    location: {
      state: '',
      city: '',
      details: '',
    },
  };

  constructor(
    private ownerService: OwnerService,
    private propertyService: PropertyService,
    private filterService: FilterService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.getPropertyType();
    this.getAllAmenities();
    if (this.property) {
      this.form = this.property;
    }
    this.user = this.sessionService.getUser()
      ? JSON.parse(this.sessionService.getUser())
      : null;
  }

  getPropertyType(): void {
    this.filterService.findAllPropertyType().subscribe((res) => {
      this.types = res.data;
    });
  }

  getAllAmenities(): void {
    this.filterService.findAllAmenities().subscribe((res) => {
      this.amenities = res.data;
    });
  }
  closePopup() {
    this.property = null;
    this.propertyService.openAddPopup.next(false);
    this.propertyService.openEditPopup.next(false);
    this.restartForm();
  }

  restartForm() {
    this.form = {
      name: '',
      description: '',
      pricePerNight: 0,
      type: '',
      bedroom: 1,
      bathroom: 1,
      kitchen: 1,
      location: '',
    };
  }
  isPropertyExisted(username: string): boolean {
    return false;
  }

  onSubmit() {
    const {
      name,
      description,
      pricePerNight,
      propertyType,
      bedroom,
      bathroom,
      kitchen,
      location,
    } = this.form;

    const selectedAmenities = this.amenities.filter(
      (amenity: { checked: any }) => amenity.checked
    );
    if (!this.property) {
      if (this.isPropertyExisted(name)) {
        alert('Property already existed! Please enter another name');
      } else {
        const property = {
          name: name,
          description: description,
          avgRating: 0,
          ratingNum: 0,
          pricePerNight: this.form.pricePerNight,
          bedroom: 1,
          bathroom: 1,
          kitchen: 1,
          user: this.user,
          location: {
            state: location.state,
            city: location.city,
            details: location.details,
          },
          propertyType: {
            id: propertyType.id,
            name: propertyType.name,
          },
          propertyImages: [],
          amenities: selectedAmenities,
        };
        this.handleAddProperty(property);
        this.propertyService.openAddPopup.next(false);
        this.restartForm();
      }
    } else {
      const updatedProperty = {
        name: name,
        description: description,
        avgRating: 0,
        ratingNum: 0,
        pricePerNight: this.form.pricePerNight,
        bedroom: bedroom,
        bathroom: bathroom,
        kitchen: kitchen,
        user: this.user,
        location: {
          state: location.state,
          city: location.city,
          details: location.details,
        },
        propertyType: {
          id: propertyType.id,
          name: propertyType.name,
        },
        propertyImages: this.property.propertyImages,
        amenities: selectedAmenities,
      };
      this.handleUpdateProperty(this.property.id, updatedProperty);
      this.propertyService.openEditPopup.next(false);
      this.restartForm();
    }
  }

  handleAddProperty(property: any): void {
    this.ownerService.addNewProperty(property).subscribe((res) => {
      console.log('add ' + JSON.stringify(res));
      this.propertyService.newProperty.next(res.data);
    });
  }

  handleUpdateProperty(id: number, property: any): void {
    this.ownerService.updateProperty(property, id).subscribe((res) => {});
  }
}
