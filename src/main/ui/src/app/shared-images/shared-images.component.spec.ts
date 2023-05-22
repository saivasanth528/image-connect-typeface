import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SharedImagesComponent } from './shared-images.component';

describe('SharedImagesComponent', () => {
  let component: SharedImagesComponent;
  let fixture: ComponentFixture<SharedImagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SharedImagesComponent]
    });
    fixture = TestBed.createComponent(SharedImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
