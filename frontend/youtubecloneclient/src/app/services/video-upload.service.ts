import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UploadVideoResponse } from '../models/upload-video-response';

@Injectable({
  providedIn: 'root',
})
export class VideoUploadService {
  private baseUrl: string = environment.baseUrl;
  constructor(private _httpClient: HttpClient) {}

  public uploadVideo(fileEntry: File): Observable<UploadVideoResponse> {
    const formData = new FormData();
    formData.append('file', fileEntry, fileEntry.name);
    return this._httpClient.post<UploadVideoResponse>(
      `${this.baseUrl}/videos/upload-video`,
      formData
    );
  }
}
