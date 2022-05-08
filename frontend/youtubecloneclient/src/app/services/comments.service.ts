import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommentDto } from '../models/comment-dto';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  private baseUrl: string = environment.baseUrl;

  constructor(private _httpClient: HttpClient) {}

  postComment(commentDto: any, videoId: string): Observable<any> {
    return this._httpClient.post<any>(
      `${this.baseUrl}/videos/${videoId}/comment`,
      commentDto
    );
  }

  getAllComments(videoId: string): Observable<Array<CommentDto>> {
    return this._httpClient.get<CommentDto[]>(
      `${this.baseUrl}/videos/${videoId}/comment`
    );
  }
}
