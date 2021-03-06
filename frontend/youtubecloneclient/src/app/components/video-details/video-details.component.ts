import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { VideoUploadService } from 'src/app/services/video-upload.service';

@Component({
  selector: 'app-video-details',
  templateUrl: './video-details.component.html',
  styleUrls: ['./video-details.component.css'],
})
export class VideoDetailsComponent implements OnInit {
  videoId: string;
  videoUrl: string = '';
  videoTitle!: string;
  videoDescription!: string;
  tags: Array<string> = [];
  videoAvailable: boolean = false;
  likedCount: number = 0;
  disLikedCount: number = 0;
  viewCount: number = 0;
  showSubscribeButton: boolean = true;
  showUnSubscribeButton: boolean = false;

  constructor(
    private _videoUploadService: VideoUploadService,
    private _userService: UserService,
    private _activatedRoute: ActivatedRoute
  ) {
    this.videoId = this._activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.onGetVideoDetails();
  }

  onGetVideoDetails() {
    this._videoUploadService
      .getVideoDetails(this.videoId)
      .subscribe((response) => {
        this.videoUrl = response.videoUrl;
        this.videoTitle = response.title;
        this.videoDescription = response.description;
        this.tags = response.tags;
        this.videoAvailable = true;
        this.likedCount = response.likedCount;
        this.disLikedCount = response.disLikedCount;
        this.viewCount = response.viewCount;
      });
  }

  /**
   * likeVideo
   */
  public likeVideo() {
    this._videoUploadService.likeVideo(this.videoId)
    .subscribe((response)=>{
      this.likedCount=response.likedCount;
      this.disLikedCount=response.disLikedCount;
    });
  }

  /**
   * disLikeVideo
   */
  public disLikeVideo() {
    this._videoUploadService.dislikeVideo(this.videoId)
    .subscribe((response)=>{
      this.likedCount=response.likedCount;
      this.disLikedCount=response.disLikedCount;
    });
  }

  /**
   * subscribeToUser
   */
  public subscribeToUser() {
    const userId=this._userService.getUserId();
    console.log("userid: "+userId);

    this._userService.subscribeToUser(userId)
    .subscribe(
      (response)=>{
        this.showUnSubscribeButton=response;
        this.showSubscribeButton=false
      });
  }

  /**
   * unSubscribeToUser
   */
  public unSubscribeToUser() {
    const userId=this._userService.getUserId();
    this._userService.unSubscribeToUser(userId)
    .subscribe(
      (response)=>{
        this.showSubscribeButton=response;
        this.showUnSubscribeButton=false
      });
  }

}
