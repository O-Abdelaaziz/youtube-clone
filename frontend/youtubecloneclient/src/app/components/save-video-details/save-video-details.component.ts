import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { ActivatedRoute } from '@angular/router';
import { VideoUploadService } from 'src/app/services/video-upload.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { VideoDto } from 'src/app/models/video-dto';

@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrls: ['./save-video-details.component.css'],
})
export class SaveVideoDetailsComponent implements OnInit {
  public videoDetailsFrom: FormGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    videoStatus: new FormControl(''),
  });
  public selectable = true;
  public removable = true;
  public addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  public tags: string[] = [];

  public selectedFile?: File;
  public selectedFileName: string = '';
  public selectedFilePath: string | undefined;
  public isFileSelected: boolean = false;
  public message: string = '';
  public videoId: string = '';
  public videoUrl: string = '';
  public thumbnailUrl: string = '';

  constructor(
    private _videoUploadService: VideoUploadService,
    private _snackBar: MatSnackBar,
    private _formBuilder: FormBuilder,
    private _activatedRoute: ActivatedRoute
  ) {
    this.videoId = this._activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.videoDetailsFrom = this._formBuilder.group({
      title: [''],
      description: [''],
      videoStatus: [''],
    });

    this.onGetVideoDetails();
  }

  onGetVideoDetails() {
    this._videoUploadService
      .getVideoDetails(this.videoId)
      .subscribe((response) => {
        console.log(response);
        this.videoUrl = response.videoUrl;
        this.thumbnailUrl = response.thumbnailUrl;
      });
  }
  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  get videoDetailsFromControls(): { [key: string]: AbstractControl } {
    return this.videoDetailsFrom.controls;
  }

  public onSaveVideoDetails() {
    const videoMetaData: VideoDto = {
      id: this.videoId,
      title: this.videoDetailsFromControls?.['title'].value,
      description: this.videoDetailsFromControls?.['description'].value,
      videoStatus: this.videoDetailsFromControls?.['videoStatus'].value,
      videoUrl: this.videoUrl,
      thumbnailUrl: this.thumbnailUrl,
      tags: this.tags,
      likedCount: 0,
      disLikedCount: 0,
      viewCount: 0,
    };
    console.log('onSaveVideoDetails vidurl ' + this.videoUrl);
    console.log('onSaveVideoDetails image url' + this.thumbnailUrl);
    this._videoUploadService
      .saveVideoDetails(videoMetaData)
      .subscribe((response) => {
        this.openSnackBar('Video details updated successfully', 'OK');
      });
  }

  onFileSelected($event: Event) {
    const input = $event.target as HTMLInputElement;
    if (!input.files?.length) {
      return;
    }

    const file = input.files[0];
    var mimeType = file.type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = 'Only images are supported.';
      return;
    }
    this.selectedFile = file;
    this.selectedFileName = file.name;
    this.previewImage($event);
    this.isFileSelected = true;
  }

  onUpload() {
    this._videoUploadService
      .uploadThumbnail(this.selectedFile!, this.videoId)
      .subscribe((response) => {
        this.thumbnailUrl = response;
        console.log('show an upload success notification' + response);
        this.openSnackBar('The thumbnail uploaded successfully.', 'OK');
      });
  }

  previewImage(event: any) {
    if (event.target.files && event.target.files[0]) {
      const reader = new FileReader();
      reader.readAsDataURL(event.target.files[0]);
      reader.onload = (e: any) => {
        this.selectedFilePath = e.target.result;
        this.message = '';
      };
    }
  }

  OnRemoveImage() {
    this.selectedFile = undefined;
    this.selectedFileName = '';
    this.selectedFilePath = '';
    this.isFileSelected = false;
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
}
