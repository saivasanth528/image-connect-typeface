import { Component, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImagePopupComponent } from '../image-popup/image-popup.component';
import { MatDialog } from '@angular/material/dialog';
import { ImageService } from '../services/image.service';

@Component({
  selector: 'app-owned-images',
  templateUrl: './owned-images.component.html',
  styleUrls: ['./owned-images.component.css']
})
export class OwnedImagesComponent {
  username: any;
  images: any[] = []; 
  selectedImage: any; 

  @ViewChild('imagePopupTemplate', { static: false }) imagePopupTemplate: TemplateRef<any> | undefined;
  

  constructor(private route: ActivatedRoute, private dialog: MatDialog, 
    private imageService: ImageService) { }


  ngOnInit() {
  
    this.route.parent?.paramMap.subscribe(params => {
      this.username = params.get('username');
      this.fetchImages();
    });
    
    
  }

  fetchImages() {
    this.imageService.getImages(this.username)
      .subscribe(images => {
        this.images = images;
      })
  }

  openImagePopup(image: any) {
    this.selectedImage = image;
    const dialogRef = this.dialog.open(ImagePopupComponent, {
      width: '500px',
      data: {
        image: this.selectedImage.mediaUrl,
        text: this.selectedImage.text,
        caption: this.selectedImage.caption,
        imageId: this.selectedImage.id,
        username: this.username
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      // Handle any actions after the dialog is closed
    });
  }
}
