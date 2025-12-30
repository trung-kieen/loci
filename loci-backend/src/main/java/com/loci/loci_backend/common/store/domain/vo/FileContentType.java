package com.loci.loci_backend.common.store.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record FileContentType(String contentType) implements ValueObject<String> {

  @Override
  public String value() {
    return this.contentType();
  }
}
