import { Component, inject, signal } from '@angular/core';
import { KeycloakAuthenticationManager } from './core/auth/keycloak-auth-manager';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  standalone: false,
})
export class App {
  protected title = 'Loci';
  private authService = inject(KeycloakAuthenticationManager);
  private router = inject(Router);
  public logout() {
    this.authService.logout();
  }

  isSidebarOpen = signal(false);

  constructor() {
    // Auto-close app sidebar khi navigate trên mobile
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        if (window.innerWidth < 1024) {
          // lg breakpoint
          this.isSidebarOpen.set(false);
        }
      });
  }
  openSettings(){
    this.router.navigate(['/user/settings']);
  }
  openNofications(){
    this.router.navigate(['/notifications']);
  }

  toggleSidebar() {
    this.isSidebarOpen.update((value) => !value);
  }

  closeSidebar() {
    this.isSidebarOpen.set(false);
  }
}
