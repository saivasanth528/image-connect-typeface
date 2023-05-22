import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnedImagesComponent } from './owned-images.component';

describe('OwnedImagesComponent', () => {
  let component: OwnedImagesComponent;
  let fixture: ComponentFixture<OwnedImagesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OwnedImagesComponent]
    });
    fixture = TestBed.createComponent(OwnedImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
