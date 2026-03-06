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

package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import com.loci.loci_backend.core.groups.infrastructure.primary.payload.RestGroupProfile;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class RestCreatedGroupConversationResponse {
  private final RestChatReference chat;
  private final RestGroupProfile group;

  @Builder(style = BuilderStyle.STAGED)
  public RestCreatedGroupConversationResponse(RestChatReference chat, RestGroupProfile group) {
    this.chat = chat;
    this.group = group;
  }

}
