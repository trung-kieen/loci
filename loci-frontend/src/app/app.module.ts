import {
  NgModule,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AccessDenied } from './core/components/access-denied/access-denied';

import { App } from './app';
import { SharedModule } from './shared/shared.module';
import { OtherProfile } from './features/user/components/other-profile/other-profile';
import { CoreModule } from './core/core.module';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app.routes';

@NgModule({
  imports: [
    CoreModule.forRoot(),
    AppRoutingModule,
    SharedModule,
    HttpClientModule,
    BrowserModule,
    AccessDenied,
    OtherProfile,
],
  declarations: [App],
  bootstrap: [App],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    // provideRouter(routes),
  ],
  exports: [],
})
export class AppModule {}
