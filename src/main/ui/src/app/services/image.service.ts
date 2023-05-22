import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getImages(userName: string): Observable<any[]> {
    const url = `${this.apiUrl}/images/owned/${userName}`;
    return this.http.get<any[]>(url);
  }

  getSharedImagesOfUser(userName: string): Observable<any[]> {
    const url = `${this.apiUrl}/image-share/shared/${userName}`;
    return this.http.get<any[]>(url);
  }

  getCommentsOfImage(imageId: string): Observable<any[]> {
    const url = `${this.apiUrl}/comments/${imageId}`;
    return this.http.get<any[]>(url);
  }

  postComment(data: any): Observable<any[]> {
    const url = `${this.apiUrl}/comments/create`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<any>(url, data, { headers });
  }
}
