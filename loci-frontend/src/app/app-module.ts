import {
  APP_INITIALIZER,
  ErrorHandler,
  NgModule,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  provideHttpClient,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AccessDenied } from './access-denied/access-denied';
import { UserInfo } from './user-info/user-info';
import {
  KeycloakAngularModule,
  KeycloakService,
  provideKeycloak,
} from 'keycloak-angular';

import Keycloak from 'keycloak-js';
import { AppRoutingModule, routes } from './app.routes';
import { App } from './app';
import { MatInputModule } from '@angular/material/input';
import { environment } from '../environments/environments';
import { initializeKeycloak } from '../utils/app-init';
import { ErrorHandlerService } from './service/error-handler.service';
import { HttpErrorInterceptor } from '../core/middleware/http-error.interceptor';

@NgModule({
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    // RouterModule.forRoot(routes),
    HttpClientModule,
    KeycloakAngularModule,
    BrowserModule,
    BrowserAnimationsModule,
    AccessDenied,
    MatInputModule,
    UserInfo,
    App
  ],
  // declarations: [App],
  // bootstrap: [App],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(withInterceptorsFromDi()),
    // provideKeycloak({
    //   config: {
    //     url: environment.keycloak.issuer,
    //     realm: environment.keycloak.realm,
    //     clientId: environment.keycloak.clientId,
    //   },
    // }),
    // {
    //   provide: APP_INITIALIZER,
    //   useFactory: initializeKeycloak,
    //   multi: true,
    //   // deps: [Keycloak]
    //   deps: [KeycloakService]

    // },
    // {
    //   provide: ErrorHandler,
    //   useClass: ErrorHandlerService
    // },
    // {
    //   provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true
    // }
  ],
  exports: [UserInfo],
})
export class AppModule { }
