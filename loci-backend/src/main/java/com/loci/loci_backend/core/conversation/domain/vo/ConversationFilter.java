package com.loci.loci_backend.core.conversation.domain.vo;

import lombok.Getter;

@Getter
public enum ConversationFilter {
  INBOX("inbox"), UNREAD("unread"), FOLLOW_UPS("follow-ups"), ARCHIVED("archived");

  private String value;

  private ConversationFilter(String value) {
    this.value = value;
  }
}
