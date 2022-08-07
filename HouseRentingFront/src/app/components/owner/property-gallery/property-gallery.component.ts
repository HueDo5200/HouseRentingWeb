import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HeaderServiceService } from 'src/app/services/header-service.service';
import { PropertyService } from 'src/app/services/property.service';
import { SessionService } from 'src/app/services/session.service';
@Component({
  selector: 'app-property-gallery',
  templateUrl: './property-gallery.component.html',
  styleUrls: ['./property-gallery.component.css']
})
export class PropertyGalleryComponent implements OnInit {
  property:any;
  id:number = 0;
  constructor(private sessionService: SessionService, private propertyService: PropertyService, private route: ActivatedRoute, private headerService: HeaderServiceService) { }

  ngOnInit(): void {
    const user = this.sessionService.getUser();
    if (user) {
      const userData = JSON.parse(user);
      if(userData.role.name = 'owner') {
        this.headerService.updateOwnerHeader();
      }
      
    }
     let id = this.route.snapshot.params['id'];
     this.getAllPropertyImages(id);
  }
  getAllPropertyImages(id:number): void {
      this.propertyService.getImagesOfProperty(id).subscribe(res => {
        this.property = res.data;
      })
  }

  deletePropertyImage(imageId:number) {
      this.propertyService.deleteImageOfProperty(imageId).subscribe(res => {
        console.log('res ' + JSON.stringify(res));
        if(res.message) {
          alert(res.message);
          this.updateImageList(imageId);
        }
      })
  }

  updateImageList(imageId: number): void {
      this.property.propertyImages = this.property.propertyImages.filter((image: { id: number; }) => image.id != imageId);
  }

}
