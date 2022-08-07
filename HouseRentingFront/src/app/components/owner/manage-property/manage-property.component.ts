import { identifierName, ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { OwnerService } from 'src/app/services/owner.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';
import { UploadImageService } from 'src/app/services/upload-image.service';

@Component({
  selector: 'app-manage-property',
  templateUrl: './manage-property.component.html',
  styleUrls: ['./manage-property.component.css'],
})
export class ManagePropertyComponent implements OnInit {
  owner:any;
  isOwner:boolean = true;
  ownerId:number = 0;
  propertyList: any;
  updatedProperty: any;
  selectedImage: any;
  selectedImages!:FileList;
  addPopup: boolean = false;
  editPopup: boolean = false;
  imagePopup: boolean = false;
  propertyId: number = 0;
 
  constructor( private ownerService: OwnerService,
    private propertyService: PropertyService,
    private sessionService: SessionService,
    private headerService: HeaderServiceService,
    private uploadImageService:UploadImageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const userData = this.sessionService.getUser();
    if (userData) {
      this.owner = JSON.parse(userData);
      if(this.owner.role.name == 'owner') {
        this.isOwner = true;
        this.ownerId = this.owner.id;
        this.getAllProperty(this.ownerId);
        this.headerService.updateOwnerHeader();
      }
    }
     
    this.propertyService.openAddPopup.subscribe((res) => {
      this.addPopup = res;
    });
    this.propertyService.openEditPopup.subscribe((res) => {
      this.editPopup = res;
    });
    this.propertyService.newProperty.subscribe(res => {
      if(res && Object.keys(res).length > 0) {
        this.propertyList.push(res);
      }
    })
  }

  getAllProperty(id:number): void {
    this.ownerService.findAllProperty(id).subscribe((res) => {
      console.log('data ' + JSON.stringify(res));
      this.propertyList = res.data;
    });
  }

  deleteProperty(id: number): void {
    this.ownerService.deleteProperty(id).subscribe((res) => {
      const index = this.propertyList.findIndex(
        (item: { id: any }) => item.id == id
      );
      this.propertyList.splice(index, 1);
    });
  }

  openEditPopup(property: any) {
    this.propertyService.openEditPopup.next(true);
    this.updatedProperty = property;
  }

  openAddPopup() {
    this.propertyService.openAddPopup.next(true);
    this.updatedProperty = null;
  }

  openImagePopup(id: number): void {
    this.imagePopup = true;
    this.propertyId = id;
  }

  closeImagePopup(): void {
    this.imagePopup = false;
  }

  processImage() {
    for(let i = 0; i < this.selectedImages.length; i++) {
      console.log('name ' + this.selectedImages[0].name)
        this.uploadImageService.upload(this.selectedImages[i], this.propertyId).subscribe(res => {
          console.log('success ' + JSON.stringify(res));
          const index = this.propertyList.findIndex(
                  (item: { id: number }) => item.id == this.propertyId
                );
                if (index != -1) {
                  if (this.propertyList[index].propertyImages.length > 0) {
                    this.propertyList[index].propertyImages[0].path =
                      this.selectedImages[0].name;
                  } else {
                    this.propertyList[index].propertyImages.push({
                      name: this.selectedImages[0].name,
                    });
                  }
               
                  const newFood = {
                    id: this.propertyList[index].id,
                    name: this.propertyList[index].name,
                    description: this.propertyList[index].description,
                    avgRating: this.propertyList[index].avgRating,
                    ratingNum: this.propertyList[index].ratingNum,
                    pricePerNight: this.propertyList[index].pricePerNight,
                    bedroom: this.propertyList[index].bedroom,
                    bathroom: this.propertyList[index].bathroom,
                    kitchen: this.propertyList[index].kitchen,
                    user: this.propertyList[index].user,
                    location: this.propertyList[index].location,
                    propertyType: this.propertyList[index].propertyType,
                    propertyImages: this.propertyList[index].propertyImages,
                  };
                  this.propertyList.splice(index, 1, newFood);
                  console.log('this property ' + JSON.stringify(this.propertyList));
                } else {
                }
          this.imagePopup = false;
        })
    }
    // const formData = new FormData();
    // formData.append('image', this.selectedImages, this.selectedImages[0].name);
    // formData.append('image', this.selectedImages);
    // this.ownerService
    //   .uploadImage(formData, this.propertyId)
    //   .subscribe((res) => {
    //     const index = this.propertyList.findIndex(
    //       (item: { id: number }) => item.id == this.propertyId
    //     );
    //     if (index != -1) {
    //       if (this.propertyList[index].propertyImages.length > 0) {
    //         this.propertyList[index].propertyImages[0].path =
    //           this.selectedImage.name;
    //       } else {
    //         this.propertyList[index].propertyImages.push({
    //           name: this.selectedImage.name,
    //         });
    //       }
    //       const newFood = {
    //         id: this.propertyList[index].id,
    //         name: this.propertyList[index].name,
    //         description: this.propertyList[index].description,
    //         avgRating: this.propertyList[index].avgRating,
    //         ratingNum: this.propertyList[index].ratingNum,
    //         pricePerNight: this.propertyList[index].pricePerNight,
    //         bedroom: this.propertyList[index].bedroom,
    //         bathroom: this.propertyList[index].bathroom,
    //         kitchen: this.propertyList[index].kitchen,
    //         user: this.propertyList[index].user,
    //         location: this.propertyList[index].location,
    //         propertyType: this.propertyList[index].propertyType,
    //         propertyImages: this.propertyList[index].propertyImages,
    //       };
    //       this.propertyList.splice(index, 1, newFood);
    //     } else {
    //     }
    //     this.imagePopup = false;
    //   });
  }

  // fileEvent(event: any) {
  //   this.selectedImage = event.target.files[0];
  // }
  fileEvent(event: any) {
    this.selectedImages = event.target.files;
  }

  onSearchSubmit(event: any) {
    let word: string = event.target.value;
    if (word !== '') {
      this.filterProperty(word);
    } else {
      this.getAllProperty(this.ownerId);
    }
  }

  filterProperty(searchWord: string): void {
    this.propertyList = this.propertyList.filter(
      (property: {
        id: number;
        name: string;
      
      }) =>
        property.name
          .split(' ')
          .join('')
          .toLowerCase()
          .includes(searchWord.split(' ').join('').toLowerCase()) ||
        searchWord.includes(property.name)
    );
  }

  openImageGallery(id:number):void {
      this.router.navigate([`/property/${id}/gallery`]);
  }
}
