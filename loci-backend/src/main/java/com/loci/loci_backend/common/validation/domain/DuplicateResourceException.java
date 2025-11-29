package com.loci.loci_backend.common.validation.domain;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {
  private Object conflictingField;

  public DuplicateResourceException() {
    super();
  }

  public DuplicateResourceException(String message, Object conflictingField) {
    super(message);
    this.conflictingField = conflictingField;
  }

  public DuplicateResourceException(Object conflictingField) {
    super();
    this.conflictingField = conflictingField;
  }

}
