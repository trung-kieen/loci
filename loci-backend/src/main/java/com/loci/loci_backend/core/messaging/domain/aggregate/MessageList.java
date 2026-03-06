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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;

import org.hibernate.query.SortDirection;
import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageList {

  private List<Message> messages;
  private boolean hasMore;

  @Nullable
  private Optional<PublicId> nextBeforeMessageId;

  @Builder(style = BuilderStyle.STAGED)
  public MessageList(List<Message> messages, boolean hasMore, PublicId nextBeforeMessageId,
      SortDirection sortDirection) {

    if (sortDirection.equals(SortDirection.DESCENDING)) {
      Collections.reverse(messages);
    }
    this.messages = messages;
    this.hasMore = hasMore;
    this.nextBeforeMessageId = Optional.ofNullable(nextBeforeMessageId);
  }

}
