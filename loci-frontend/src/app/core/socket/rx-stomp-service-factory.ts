import {
  RxStomp,
  RxStompConfig,
} from '@stomp/rx-stomp';
import { KeycloakAuthenticationManager } from '../auth/keycloak-auth-manager';
import { RxStompAdapterService } from './rx-stomp.service';


export function rxStompServiceFactory(
  authService: KeycloakAuthenticationManager, rxStompConfig: RxStompConfig
): RxStomp {
  const rxStomp = new RxStompAdapterService(authService);
  rxStomp.configure(rxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
