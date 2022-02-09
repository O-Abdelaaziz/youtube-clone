import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  public isAuthenticated: boolean = false;
  constructor(public _oidcSecurityService: OidcSecurityService) {}

  ngOnInit(): void {
    this._oidcSecurityService.isAuthenticated$.subscribe((response) => {
      this.isAuthenticated = response.isAuthenticated;
    });

    console.log("id: " + environment.env.CLIENT_ID);

  }

  public onLogin(){
    this._oidcSecurityService.authorize();
  }

  public onLogout(){
    this._oidcSecurityService.logoffAndRevokeTokens();
  }
}
