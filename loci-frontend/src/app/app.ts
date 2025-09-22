import { Component, inject } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { RouterOutlet } from '@angular/router';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [RouterOutlet, MatLabel, MatInput, MatFormField]

})
export class App {

  protected title = 'loci-frontend';
  private authService = inject(AuthService);
  public logout() {
    this.authService.logout();
  }
}
