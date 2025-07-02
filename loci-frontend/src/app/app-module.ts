import { APP_INITIALIZER, NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { App } from './app';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AccessDenied } from './access-denied/access-denied';
import { UserInfo } from './user-info/user-info';
import { KeycloakAngularModule, KeycloakService, provideKeycloak } from 'keycloak-angular';
import { environment } from '../environments/environments';
import { initializeKeycloak } from '../utils/app-init';
import { AppRoutingModule } from './app.routes';

@NgModule({
  declarations: [
    AccessDenied,
    UserInfo,
    App
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    RouterOutlet,
    FormsModule,
    HttpClientModule,
    KeycloakAngularModule

  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withInterceptorsFromDi()),
    provideKeycloak({
      config: {
        url: environment.keycloak.issuer,
        realm: environment.keycloak.realm,
        clientId: environment.keycloak.clientId
      }
    }),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ],
  bootstrap: [
    App
  ]
})
export class AppModule { }
