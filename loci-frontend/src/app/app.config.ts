import {
  APP_INITIALIZER,
  ApplicationConfig,
  ErrorHandler,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environments';
import { initializeKeycloak } from '../utils/app-init';
import { provideKeycloak } from '../auth/keycloak.provider';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(withInterceptorsFromDi()),
    provideRouter(routes),
    provideKeycloak({
      config: {
        url: environment.keycloak.issuer,
        realm: environment.keycloak.realm,
        clientId: environment.keycloak.clientId,
      },
    }),
    // provideKeycloak({
    //   config: {
    //     url: environment.keycloak.issuer,
    //     realm: environment.keycloak.realm,
    //     clientId: environment.keycloak.clientId,
    //   },
    // }),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      // deps: [Keycloak]
      deps: [KeycloakService]

    },
  ],
};


