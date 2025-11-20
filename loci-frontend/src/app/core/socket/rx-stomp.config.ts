import { RxStompConfig } from '@stomp/rx-stomp';
import { environment } from '../../../environments/environments';

export const rxStompConfig: RxStompConfig = {
  // Which server?
  // brokerURL: 'ws://localhost:8080/api/v1/ws',
  brokerURL: environment.socketEndpoint,
  // brokerURL: 'http://localhost:8080/ws',

  // Headers (typical keys: login, passcode, host)
  // connectHeaders: {
  //   login: 'guest',
  //   passcode: 'guest',
  // },


  // Heartbeats (ms). Set 0 to disable.
  heartbeatIncoming: 2000,
  heartbeatOutgoing: 20000,

  // Reconnect delay (ms). Set 0 to disable.
  reconnectDelay: 200,

  // Console diagnostics (avoid in production)
  debug: (msg: string): void => {
    console.log(new Date(), msg);
  },
};
