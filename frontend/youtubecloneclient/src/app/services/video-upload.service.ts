import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class VideoUploadService {
  private baseUrl: string = environment.baseUrl;
  constructor(private _httpClient: HttpClient) {}

  public uploadVideo(fileEntry: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', fileEntry, fileEntry.name);
    return this._httpClient.post(
      `${this.baseUrl}/videos/upload-video`,
      formData
    );
  }
}
