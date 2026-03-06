/**
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { RxStompConfig } from '@stomp/rx-stomp';
import { environment } from '../../../environments/environments';

export const rxStompConfig: RxStompConfig = {
  // Which server?
  // brokerURL: 'ws://localhost:8080/api/v1/ws',
  brokerURL: environment.socketEndpoint,
  // brokerURL: 'http://localhost:8080/ws',

  // Headers (typical keys: login, passcode, host)
  // connectHeaders: {
  //   login: 'admin',
  //   passcode: 'admin',
  // },


  // Heartbeats (ms). Set 0 to disable.
  heartbeatIncoming: 10000,
  heartbeatOutgoing: 10000,



  // Reconnect delay (ms). Set 0 to disable.
  reconnectDelay: 2000,

  // Console diagnostics (avoid in production)
  // debug: (msg: string): void => {
  //   console.log(new Date(), msg);
  // },
};
