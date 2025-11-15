import { Component, inject } from '@angular/core';
import { AuthService } from './core/auth/auth.service';
import { OtherProfile } from './features/user/components/other-profile/other-profile';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
})
export class App {

  protected title = 'loci-frontend';
  private user = {
    avatar: "assets/avatar1.svg",
    name: "kai"
  }
  private authService = inject(AuthService);
  public logout() {
    this.authService.logout();
  }
}
