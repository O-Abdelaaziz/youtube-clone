import { Component, OnInit } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isAuthenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService) {
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
    })
  }

  onLogin() {
    this.oidcSecurityService.authorize();
  }

  onLogout() {
    this.oidcSecurityService.logoff();
  }

  logOff() {
    this.oidcSecurityService.logoffAndRevokeTokens();
    this.oidcSecurityService.logoffLocal();
  }
}
