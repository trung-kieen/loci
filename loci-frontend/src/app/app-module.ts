import {
  APP_INITIALIZER,
  NgModule,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  HttpClientModule,
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AccessDenied } from './core/components/access-denied/access-denied';
import { UserInfo } from './core/components/user-info/user-info';
import {
  KeycloakAngularModule,
  KeycloakService,
  provideKeycloak,
} from 'keycloak-angular';

// import Keycloak from 'keycloak-js';
import { AppRoutingModule, routes } from './app.routes';
import { App } from './app';
import { MatInputModule } from '@angular/material/input';
import { environment } from '../environments/environments';
import { initializeKeycloak } from './core/auth/keycloak/keycloak.init';
import { provideRouter } from '@angular/router';
import { SharedModule } from './shared/shared.module';

@NgModule({
  imports: [
    AppRoutingModule,
    BrowserModule,
    SharedModule,
    FormsModule,
    HttpClientModule,
    KeycloakAngularModule,
    BrowserModule,
    BrowserAnimationsModule,
    AccessDenied,
    MatInputModule,
    UserInfo,
],
  declarations: [App],
  bootstrap: [App],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withInterceptorsFromDi()),
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
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      // deps: [Keycloak]
      deps: [KeycloakService],
    },
  ],
  exports: [UserInfo],
})
export class AppModule {}
