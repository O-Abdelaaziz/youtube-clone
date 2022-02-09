import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';
import { environment } from 'src/environments/environment';

@NgModule({
  imports: [
    AuthModule.forRoot({
      config: {
        authority: 'https://o-abdelaaziz.us.auth0.com',
        redirectUrl: window.location.origin,
        postLogoutRedirectUri: window.location.origin,
        clientId: environment.env.CLIENT_ID,
        scope: 'openid profile offline_access email',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        // historyCleanupOff: false,
        secureRoutes: ['http://localhost:8090/'],
        customParamsAuthRequest: {
          audience: 'http://localhost:8090/',
        },
      },
    }),
  ],
  exports: [AuthModule],
})
export class AuthConfigModule {}
