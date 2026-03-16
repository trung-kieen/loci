/*
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

package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.core.messaging.domain.repository.DirectMessageNotifier;
import com.loci.loci_backend.core.messaging.domain.repository.GroupMessageNotifier;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageNotifierFactory {
  private final DirectMessageNotifier directMessageNotifier;
  private final GroupMessageNotifier groupMessageNotifier;

  public DirectMessageNotifier forDirectConversation() {
    return directMessageNotifier;
  }

  public GroupMessageNotifier forGroupConversation() {
    return groupMessageNotifier;
  }
}
