import { Component, OnInit, inject } from '@angular/core';
import { WebApiService } from '../../../api/web-api.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.html',
  styleUrl: './user-info.css',
})
export class UserInfo implements OnInit {
  message = 'N/A';

  private authService = inject(AuthService);
  private webApiService = inject(WebApiService);
  // private httpClient = inject(HttpClient);
  async ngOnInit() {
    this.message = await this.authService.getUsername();
    console.log(this.message);

    this.webApiService.getUserInfo().subscribe({
      next: (data) => {
        this.message = data.content;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
