import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { FeaturedComponent } from './components/featured/featured.component';
import { HistoryComponent } from './components/history/history.component';
import { HomeComponent } from './components/home/home.component';
import { LikedVideosComponent } from './components/liked-videos/liked-videos.component';
import { SaveVideoDetailsComponent } from './components/save-video-details/save-video-details.component';
import { SubscriptionComponent } from './components/subscription/subscription.component';
import { UnauthorizedComponent } from './components/unauthorized/unauthorized.component';
import { UploadVideoComponent } from './components/upload-video/upload-video.component';
import { VideoDetailsComponent } from './components/video-details/video-details.component';
import { CallbackComponent } from './components/callback/callback.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: '', component: FeaturedComponent },
      { path: 'history', component: HistoryComponent },
      { path: 'subscriptions', component: SubscriptionComponent },
      { path: 'liked-videos', component: LikedVideosComponent },
    ],
  },
  { path: 'upload-video', component: UploadVideoComponent },
  { path: 'save-video-details/:id', component: SaveVideoDetailsComponent },
  { path: 'video-details/:id', component: VideoDetailsComponent },
  { path: 'callback', component: CallbackComponent },

  { path: 'unauthorized', component: UnauthorizedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
