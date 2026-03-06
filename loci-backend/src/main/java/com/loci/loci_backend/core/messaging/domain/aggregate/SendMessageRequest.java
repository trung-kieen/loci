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

package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendMessageRequest {

  private MessageContent content;

  private PublicId conversationPublicId;

  private Optional<PublicId> replyToMessagePublicId;

  private Optional<Attachment> attachment;

  @Builder(style = BuilderStyle.STAGED)
  public SendMessageRequest(MessageContent content, PublicId conversationPublicId,
      Optional<PublicId> replyToMessagePublicId, Attachment attachment) {
    this.content = content;
    this.conversationPublicId = conversationPublicId;
    this.replyToMessagePublicId = replyToMessagePublicId;
    this.attachment = Optional.ofNullable(attachment);
  }

}
