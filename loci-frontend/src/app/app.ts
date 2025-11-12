import { Component, inject } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatFormField, MatInput, MatInputModule, MatLabel } from '@angular/material/input';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  // standalone: false,
  imports: [RouterOutlet, MatInputModule, RouterOutlet, RouterLink],
  // imports: [AppModule],

})
export class App {

  protected title = 'loci-frontend';
  private authService = inject(AuthService);
  public logout() {
    this.authService.logout();
  }
}
