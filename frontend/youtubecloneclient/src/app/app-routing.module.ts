import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { HistoryComponent } from './components/history/history.component';
import { HomeComponent } from './components/home/home.component';
import { LikedVideosComponent } from './components/liked-videos/liked-videos.component';
import { SaveVideoDetailsComponent } from './components/save-video-details/save-video-details.component';
import { SubscriptionComponent } from './components/subscription/subscription.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { UploadVideoComponent } from './components/upload-video/upload-video.component';
import { VideoDetailsComponent } from './components/video-details/video-details.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: 'history', component: HistoryComponent },
      { path: 'subscriptions', component: SubscriptionComponent },
      { path: 'liked-videos', component: LikedVideosComponent },
    ],
  },
  { path: 'upload-video', component: UploadVideoComponent },
  { path: 'save-video-details/:id', component: SaveVideoDetailsComponent },
  { path: 'video-details/:id', component: VideoDetailsComponent },

  { path: 'unauthorized', component: UnauthorizedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
