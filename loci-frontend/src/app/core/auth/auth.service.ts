import { Injectable, inject } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class AuthService {
  private keycloakService = inject(KeycloakService);

  public async getBearerToken(){
    const token = await this.keycloakService.getToken();
    return  'Bearer ' + token;
  }
 public  async getUsername(): Promise<string> {
    const profile = await this.keycloakService.loadUserProfile();
    return profile.username || 'unknow user';
    // return this.keycloakService.getUsername();
  }

  public logout(): void {
    console.log("Call logout");
    console.log(this.keycloakService);
    // this.keycloakService.clearToken();
    // this.keycloakService.logout().then(() => this.keycloakService.clearToken());
    this.keycloakService.logout();
  }
}
