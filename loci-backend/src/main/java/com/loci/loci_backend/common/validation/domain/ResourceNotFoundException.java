package com.loci.loci_backend.common.validation.domain;

import jakarta.ws.rs.container.ResourceInfo;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private Object resourceId;

  public ResourceNotFoundException() {
  }

  public ResourceNotFoundException(String message, Object resourceId) {
    super(message);
    this.resourceId = resourceId;
  }

  public ResourceNotFoundException(Object resourceId) {
    super();
    this.resourceId = resourceId;
  }
}
