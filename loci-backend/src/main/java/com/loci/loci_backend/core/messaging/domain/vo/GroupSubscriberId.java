package com.loci.loci_backend.core.messaging.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

/**
 *
 */
public record GroupSubscriberId(UUID groupPublicId) implements ValueObject<String> {

  @Override
  public String value() {
    return groupPublicId.toString();
  }

}
