package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record UnreadCount(Long value) implements ValueObject<Long> {
  public UnreadCount {
    Assert.field("unread count", value).positive();
  }

  public static UnreadCount ofDefault(){
    return new UnreadCount(0L);
  }
}
