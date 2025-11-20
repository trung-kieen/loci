import {  platformBrowser } from '@angular/platform-browser';
import { AppModule } from './app/app.module';


platformBrowser()
  .bootstrapModule(AppModule)   // keep NgModule bootstrap
  .catch(err => console.error(err));
