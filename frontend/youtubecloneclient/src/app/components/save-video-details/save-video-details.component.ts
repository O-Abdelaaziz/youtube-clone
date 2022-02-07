import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { ActivatedRoute } from '@angular/router';
import { VideoUploadService } from 'src/app/services/video-upload.service';
import { MatSnackBar } from '@angular/material/snack-bar';

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

  public onSaveVideoDetails() {
    console.log('from submitted');
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
        console.log('show an upload success notification');
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

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
}
