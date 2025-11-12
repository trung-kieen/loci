import { bootstrapApplication, platformBrowser } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
bootstrapApplication(App, appConfig).catch(error => console.log(error));
// platformBrowser()
  // .bootstrapModule(AppModule)
  // .catch((err ) => console.error(err));
