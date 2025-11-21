import { IRxStompPublishParams, RxStomp, RxStompConfig } from '@stomp/rx-stomp';
import { KeycloakAuthenticationManager } from '../auth/keycloak-auth-manager';

/**
 * Help to inject authentication token from keycloak
 */
export class RxStompAdapterService extends RxStomp {
  private authService: KeycloakAuthenticationManager;
  constructor(authService: KeycloakAuthenticationManager) {
    super();
    // if (!(authService instanceof AuthService && authService.getBearerToken)) {
    if (!authService.getBearerToken) {
      console.log(Object.getOwnPropertyNames(authService));
      throw new Error('Error auth object for rxstomp');
    }
    console.log("Get auth success for rxStomp")
    this.authService = authService;
  }
  override configure(rxStompConfig: RxStompConfig): void {
    const headers = {
      Authorization: '',
    };
    this.authService.getBearerToken().then((token) => {
      headers['Authorization'] = token;
      console.log("Header ", headers.Authorization)
      super.configure({
        ...rxStompConfig,
        connectHeaders: {
          ...rxStompConfig.connectHeaders,
          ...headers,
        },
      });
    });
  }
  override publish(parameters: IRxStompPublishParams): void {
    const headers = {
      Authorization: '',
    };
    // fetch token asynchronously, then call super.publish to avoid recursive override
    this.authService.getBearerToken().then((token) => {
      console.log("Header ", headers.Authorization)
      headers['Authorization'] = token;
      super.publish({
        ...parameters,
        headers: headers,
      });
    }).catch(err => {
      super.publish({
        ...parameters,
        headers: headers,
      });
    })
  }
}
