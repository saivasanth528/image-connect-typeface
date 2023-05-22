import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import { MatTabsModule } from '@angular/material/tabs';
import { MatIconModule } from '@angular/material/icon';
import { HomepageComponent } from './homepage/homepage.component';
import { OwnedImagesComponent } from './owned-images/owned-images.component';
import { SharedImagesComponent } from './shared-images/shared-images.component';
import { ImagePopupComponent } from './image-popup/image-popup.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ImageService } from './services/image.service';
import { HttpClientModule } from '@angular/common/http';
import { NavbarComponent } from './navbar/navbar.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    OwnedImagesComponent,
    SharedImagesComponent,
    ImagePopupComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatSliderModule,
    MatTabsModule,
    MatIconModule,
    MatDialogModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    FormsModule
  
  ],
  providers: [
    ImageService,
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
