import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css'],
})
export class CallbackComponent implements OnInit {
  constructor(private _userService: UserService,
              private _router:Router) {
    this._userService.registerUser();
    this._router.navigateByUrl('');
  }

  ngOnInit(): void {}
}
