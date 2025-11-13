import { Component, inject } from '@angular/core';
import { AuthService } from './core/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
  // imports: [RouterOutlet, MatInputModule, RouterOutlet, RouterLink],
  // imports: [AppModule],

})
export class App {

  protected title = 'loci-frontend';
  private user =  {
    avatar: "assets/avatar1.svg",
    name : "kai"
  }
  private authService = inject(AuthService);
  public logout() {
    this.authService.logout();
  }
}
