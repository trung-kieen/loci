package com.loci.loci_backend.common.validation.domain;

public abstract class DomainViolationException extends RuntimeException{

  public DomainViolationException(String message) {
    super(message);
  }
  public DomainViolationException() {
    super();
  }

}
