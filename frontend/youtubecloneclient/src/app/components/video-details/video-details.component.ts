import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  constructor(
    private _videoUploadService: VideoUploadService,
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
}
