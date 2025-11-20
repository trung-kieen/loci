import { APP_INITIALIZER, ModuleWithProviders, NgModule, Optional, SkipSelf } from "@angular/core";
import { AuthService } from "./auth/auth.service";
import { KeycloakBearerInterceptor, KeycloakService } from "keycloak-angular";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { AuthGuard } from "./auth/auth.guard";
import { HttpErrorInterceptor } from "./middleware/http-error.interceptor";
import { initializeKeycloak } from "./auth/keycloak/keycloak.init";
import { RxStompService } from "./socket/rx-stomp.service";
import { rxStompServiceFactory } from "./socket/rx-stomp-service-factory";

@NgModule({
  imports: [
  ],
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import it in the AppModule only.');
    }
  }

  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        {
          provide: APP_INITIALIZER,
          useFactory: initializeKeycloak,
          multi: true,
          deps: [KeycloakService],
        },
        AuthService,
        KeycloakService, // Mark as provider in this module or auth module if separete
        AuthGuard,
        { provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: KeycloakBearerInterceptor,
          multi: true
        },
        {
          // provide: APP_INITIALIZER,
          provide: RxStompService,
          useFactory: rxStompServiceFactory,
          // multi: true,
        }
      ]
    };
  }
}
