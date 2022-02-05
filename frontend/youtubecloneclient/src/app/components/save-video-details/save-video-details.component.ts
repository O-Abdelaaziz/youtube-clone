import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

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

  constructor(private _formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.videoDetailsFrom = this._formBuilder.group({
      title: [''],
      description: [''],
      videoStatus: [''],
    });
  }
}
