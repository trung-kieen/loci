import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard extends KeycloakAuthGuard {
  protected override readonly router: Router;
  protected readonly keycloak: KeycloakService;

  /** Inserted by Angular inject() migration for backwards compatibility */
  // constructor(...args: unknown[]);

  // private override router = inject(Router);
  // protected readonly keycloak = inject

  constructor() {
    const router = inject(Router);
    const keycloak = inject(KeycloakService);

    super(router, keycloak);

    this.router = router;
    this.keycloak = keycloak;
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
  ) {
    // If not logged, redirect to login page
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
    }

    // Get the roles from keycloak
    const keycloakRoles = this.roles;

    // Get the roles from app.routing.module.ts
    const requiredRoles = route.data['roles'];

    // If page doesn't need any extra roles
    if (!(requiredRoles instanceof Array) || requiredRoles.length == 0) {
      return true;
    }

    // Check whether user has role to visit page (check keycloak roles against app.routing.module.ts config)
    if (requiredRoles.every((role) => keycloakRoles.includes(role))) {
      return true;
    }

    // If user doesn't have necessary roles, redirect to error page
    this.router.navigate(['access-denied']);
    return false;
  }
}
