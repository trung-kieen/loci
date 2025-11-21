package com.loci.loci_backend.common.websocket.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

public record BearerToken(String token) {
  public BearerToken {
    Assert.notNull("authentication header", token);
  }

  public static BearerToken fromHeader(String header) {
    Assert.field("Bearer header", header).notNull().minLength(7);
    return new BearerToken(header.substring(7));
  }
}
