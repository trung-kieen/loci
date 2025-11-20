import { Component, inject } from '@angular/core';
import { KeycloakAuthenticationManager } from './core/auth/keycloak-auth-manager';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
})
export class App {

  protected title = 'loci-frontend';
  private authService = inject(KeycloakAuthenticationManager);
  public logout() {
    this.authService.logout();
  }
}
