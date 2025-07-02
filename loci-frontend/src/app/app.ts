import { Component } from '@angular/core';
import { Route, Router, RouterModule, RouterOutlet } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
})
export class App {
  protected title = 'loci-frontend';
  constructor(private authService: AuthService) {
  }
  public logout() {
    this.authService.logout();
  }
}
