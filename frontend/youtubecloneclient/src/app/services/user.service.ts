import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl: string = environment.baseUrl;
  private userId = '';

  constructor(private _httpClient: HttpClient) {}

  subscribeToUser(userId: string): Observable<boolean> {
    return this._httpClient.post<boolean>(
      `${this.baseUrl}/users/subscribe/${userId}`,
      null
    );
  }

  unSubscribeToUser(userId: string): Observable<boolean> {
    return this._httpClient.post<boolean>(
      `${this.baseUrl}/users/unsubscribe/${userId}`,
      null
    );
  }

  registerUser() {
    this._httpClient
      .get(`${this.baseUrl}/users/register`, { responseType: 'text' })
      .subscribe((response) => {
        this.userId = response;
        console.log('response user service:' + response);
      });
  }

  public getUserId(): string {
    console.log('userid user service:' + this.userId);
    return this.userId;
  }
}
