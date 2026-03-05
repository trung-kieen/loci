package com.loci.loci_backend.core.messaging.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record UserSubcriberId(UUID userPublicId) implements ValueObject<String> {

  @Override
  public String value() {
    return userPublicId.toString();
  }


}
