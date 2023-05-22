import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { OwnedImagesComponent } from './owned-images/owned-images.component';
import { SharedImagesComponent } from './shared-images/shared-images.component';

const routes: Routes = [
  { path: 'users', children: [
    { path: ':username', component: HomepageComponent, children: [
      { path: '', redirectTo: 'owned', pathMatch: 'full' },
      { path: 'owned', component: OwnedImagesComponent },
      { path: 'shared', component: SharedImagesComponent },
    ]}
  ]}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
