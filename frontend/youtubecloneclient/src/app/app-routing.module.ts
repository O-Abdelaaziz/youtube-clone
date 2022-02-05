import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SaveVideoDetailsComponent } from './components/save-video-details/save-video-details.component';
import { UploadVideoComponent } from './components/upload-video/upload-video.component';

const routes: Routes = [
  { path: 'upload-video', component: UploadVideoComponent },
  { path: 'save-video-details/:id', component: SaveVideoDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
