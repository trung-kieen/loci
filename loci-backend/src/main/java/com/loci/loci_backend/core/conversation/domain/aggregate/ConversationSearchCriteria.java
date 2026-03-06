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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import com.loci.loci_backend.common.jpa.SortOrder;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationFilter;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationSearchCriteria {

  private UserDBId userId;
  private SortOrder order;
  private SearchQuery query;
  private ConversationFilter filter;

  @Builder
  public ConversationSearchCriteria(UserDBId userId, SearchQuery query, ConversationFilter filter) {
    this.userId = userId;
    this.query = query;
    this.filter = filter;
  }

  public static ConversationSearchCriteria from(User user, ConversationQuery conversationQuery) {
    return new ConversationSearchCriteria(user.getDbId(), conversationQuery.query(), conversationQuery.filter());
  }
}
