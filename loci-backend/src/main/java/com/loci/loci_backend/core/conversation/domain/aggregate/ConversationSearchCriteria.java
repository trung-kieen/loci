package com.loci.loci_backend.core.conversation.domain.aggregate;


import com.loci.loci_backend.common.jpa.SortOrder;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationFilter;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationQuery;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;

import org.jilt.Builder;

@Builder
public record ConversationSearchCriteria(
    UserDBId userId,
    SortOrder order,
    SearchQuery query,
    ConversationFilter filter) {

  public static ConversationSearchCriteria from(User user, SortOrder order, ConversationQuery conversationQuery) {
    return new ConversationSearchCriteria(user.getDbId(), order, conversationQuery.query(), conversationQuery.filter());
  }
}
