import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import {COMMA, ENTER} from '@angular/cdk/keycodes';

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
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];

  constructor(private _formBuilder: FormBuilder) {}

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

  public onSaveVideoDetails(){
    console.log("from submitted");

  }
}
