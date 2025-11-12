import { Provider } from "@angular/core";
import { KeycloakService } from "keycloak-angular";

export function provideKeycloak(config: {
  config: {
    url: string;
    realm: string;
    clientId: string;
  };
}): Provider[] {
  return [
    KeycloakService,
    {
      provide: 'KEYCLOAK_CONFIG',
      useValue: config.config,
    },
  ];
}
