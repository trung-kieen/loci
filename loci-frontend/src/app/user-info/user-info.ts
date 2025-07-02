import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { HttpClient } from '@angular/common/http';
import { WebApiService } from '../api/web-api.service';

@Component({
  selector: 'app-user-info',
  standalone: false,
  templateUrl: './user-info.html',
  styleUrl: './user-info.css'
})
export class UserInfo implements OnInit {

  message: string = 'N/A';

  constructor(private authService: AuthService, private webApiService: WebApiService, private http: HttpClient) { }

  ngOnInit(): void {
    this.message = this.authService.getUsername();
    console.log(this.message);


    this.webApiService.getUserInfo().subscribe({
      next: data => {
        this.message = data.content;
      }, error: err => {
        console.log(err);
      }
    });

  }
}
