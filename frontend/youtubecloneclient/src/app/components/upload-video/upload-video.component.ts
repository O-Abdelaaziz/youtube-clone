import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  NgxFileDropEntry,
  FileSystemFileEntry,
  FileSystemDirectoryEntry,
} from 'ngx-file-drop';
import { VideoUploadService } from 'src/app/services/video-upload.service';

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css'],
})
export class UploadVideoComponent implements OnInit {
  public files: NgxFileDropEntry[] = [];
  public fileUploaded: boolean = false;
  public fileEntry: FileSystemFileEntry | undefined;

  constructor(
    private _videoUploadService: VideoUploadService,
    private _router: Router
  ) {}

  ngOnInit(): void {}

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;
    for (const droppedFile of files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {
        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {
          // Here you can access the real file
          console.log(droppedFile.relativePath, file);
          this.fileUploaded = true;
          /**
          // You could upload it like this:
          const formData = new FormData()
          formData.append('logo', file, relativePath)

          // Headers
          const headers = new HttpHeaders({
            'security-token': 'mytoken'
          })

          this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
          .subscribe(data => {
            // Sanitized logo returned from backend
          })
          **/
        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any) {
    console.log(event);
  }

  public fileLeave(event: any) {
    console.log(event);
  }

  public onUploadVideo() {
    if (this.fileEntry != undefined) {
      this.fileEntry.file((file) => {
        this._videoUploadService.uploadVideo(file).subscribe((response) => {
          console.log('Video uploaded successfully.');
          this._router.navigateByUrl('/save-video-details/' + response.videoId);
        });
      });
    }
  }
}
