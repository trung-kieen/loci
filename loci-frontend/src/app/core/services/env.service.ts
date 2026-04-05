// src/app/core/services/env.service.ts
import { Injectable } from '@angular/core';
import { IEnv } from '../../../environments/env.model';
import { environment } from '../../../environments/environments';

@Injectable({ providedIn: 'root' })
export class EnvService implements IEnv {

  private readonly _env: IEnv;

  constructor() {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const win = window as any;
    const runtime = win.__env || {};

    // runtime (window.__env) overrides compiled (environment.ts)
    // environment.ts is the final fallback if neither exists
    this._env = {
      production: runtime.production ?? environment.production,
      apiUrl: runtime.apiUrl ?? environment.apiUrl,
      socketEndpoint: runtime.socketEndpoint ?? environment.socketEndpoint,
      keycloak: {
        issuer: runtime.keycloak?.issuer ?? environment.keycloak.issuer,
        realm: runtime.keycloak?.realm ?? environment.keycloak.realm,
        clientId: runtime.keycloak?.clientId ?? environment.keycloak.clientId,
      }
    };

    if (!this._env.production) {
      console.debug('[EnvService] loaded config:', this._env);
    }
  }

  get production() { return this._env.production; }
  get apiUrl() { return this._env.apiUrl; }
  get socketEndpoint() { return this._env.socketEndpoint; }
  get keycloak() { return this._env.keycloak; }
}
