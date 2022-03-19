import { Component, OnInit } from '@angular/core';
import { VideoDto } from 'src/app/models/video-dto';
import { VideoUploadService } from 'src/app/services/video-upload.service';

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.css'],
})
export class FeaturedComponent implements OnInit {
  public featuredVideos: Array<VideoDto> = [];

  constructor(private _videoService: VideoUploadService) {}

  ngOnInit(): void {
    this.onGetVideos();
  }

  onGetVideos() {
    this._videoService.getAllVideos().subscribe((response) => {
      this.featuredVideos = response;
    });
  }
}
