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
  public ConversationSearchCriteria(UserDBId userId, SortOrder order, SearchQuery query, ConversationFilter filter) {
    this.userId = userId;
    this.order = order;
    this.query = query;
    this.filter = filter;
  }

  public static ConversationSearchCriteria from(User user, SortOrder order, ConversationQuery conversationQuery) {
    return new ConversationSearchCriteria(user.getDbId(), order, conversationQuery.query(), conversationQuery.filter());
  }
}
