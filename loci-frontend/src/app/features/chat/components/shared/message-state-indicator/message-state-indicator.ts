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

import { Component, input } from '@angular/core';
import { MessageState } from '../../../models/message.model';

@Component({
  selector: 'app-message-state-indicator',
  imports: [],
  templateUrl: './message-state-indicator.html',
  styleUrl: './message-state-indicator.css',
})
export class MessageStateIndicator {
  messageState = input.required<MessageState | undefined>();

}
