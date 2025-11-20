import { AuthService } from '../auth/auth.service';
import { rxStompConfig } from './rx-stomp.config';
import { RxStompService } from './rx-stomp.service';

export function rxStompServiceFactory() {
  // const bearerToken = await authService.getBearerToken();
  // const bearerToken = "Hello world";
  const rxStomp = new RxStompService();
  // const bearerToken = await authService.getBearerToken();
  // const headers = {
  //   Authentication: bearerToken,
  // };

  rxStomp.configure({
    ...rxStompConfig,
    // connectHeaders: headers
  });
  rxStomp.activate();
  return rxStomp;
}
