package com.loci.loci_backend.core.notification.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record NotificationId(Long id) implements ValueObject<Long> {

  @Override
  public Long value() {
    return this.id;
  }
}
