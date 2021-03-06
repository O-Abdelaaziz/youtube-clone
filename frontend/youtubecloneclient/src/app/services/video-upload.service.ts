import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { UploadVideoResponse } from '../models/upload-video-response';
import { VideoDto } from '../models/video-dto';

@Injectable({
  providedIn: 'root',
})
export class VideoUploadService {
  private baseUrl: string = environment.baseUrl;
  constructor(private _httpClient: HttpClient) {}

  public uploadVideo(videoFile: File): Observable<UploadVideoResponse> {
    const formData = new FormData();
    formData.append('file', videoFile, videoFile.name);
    return this._httpClient.post<UploadVideoResponse>(
      `${this.baseUrl}/videos/upload-video`,
      formData
    );
  }

  public uploadThumbnail(
    thumbnailFile: File,
    videoId: string
  ): Observable<string> {
    const formData = new FormData();
    formData.append('file', thumbnailFile, thumbnailFile.name);
    formData.append('videoId', videoId);
    return this._httpClient.post(
      `${this.baseUrl}/videos/upload-thumbnail`,
      formData,
      {
        responseType: 'text',
      }
    );
  }

  public getVideoDetails(videoId: string): Observable<VideoDto> {
    return this._httpClient.get<VideoDto>(`${this.baseUrl}/videos/${videoId}`);
  }

  public saveVideoDetails(videoDto :VideoDto): Observable<VideoDto> {
    return this._httpClient.put<VideoDto>(`${this.baseUrl}/videos/update-video`,videoDto);
  }

  public getAllVideos():Observable<Array<VideoDto>>{
    return this._httpClient.get<Array<VideoDto>> (`${this.baseUrl}/videos`)
  }

  public likeVideo(videoId:string): Observable<VideoDto>{
    return this._httpClient.post<VideoDto>(`${this.baseUrl}/videos/${videoId}/like`,null);
  }

  public dislikeVideo(videoId:string): Observable<VideoDto>{
    return this._httpClient.post<VideoDto>(`${this.baseUrl}/videos/${videoId}/dislike`,null);
  }
}
