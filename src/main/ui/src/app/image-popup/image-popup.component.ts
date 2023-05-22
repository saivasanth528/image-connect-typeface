import { Component, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ImageService } from '../services/image.service';

@Component({
  selector: 'app-image-popup',
  templateUrl: './image-popup.component.html',
  styleUrls: ['./image-popup.component.css']
})
export class ImagePopupComponent {
  @Input() image: string = '';
  @Input() text: string = '';
  @Input() caption: string = '';

  comments: any[] = [];
  newComment: string = '';

  constructor(
    public dialogRef: MatDialogRef<ImagePopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,  private imageService: ImageService
  ) {
    this.image = data.image;
    this.text = data.text;
    this.caption = data.caption;
    this.fetchComments();
  }

  

  onClose(): void {
    this.dialogRef.close();
  }

  fetchComments(): void {
    this.imageService.getCommentsOfImage(this.data.imageId).subscribe((comments) => {
      console.log(comments);
      this.comments = comments;
    });
  }

  addComment(): void {
    // Simulating API call to add a new comment for the image
    // Replace with your actual API call
    console.log(this.newComment);
    const commentData = {
      "username": this.data.username,
      "imageId": this.data.imageId,
      "commentText": this.newComment
    }
    this.imageService.postComment(commentData).subscribe((result) => {
      console.log(result);
      this.comments.unshift(result);
    })
    // this.comments.push(this.newComment);
  
    this.newComment = '';
  }

  convertToDate(updatedAt: number[]): Date {
    const [year, month, day, hour, minute, second] = updatedAt;
    return new Date(year, month - 1, day, hour, minute, second);
  }
  
}
