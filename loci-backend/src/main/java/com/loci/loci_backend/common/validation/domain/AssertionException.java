package com.loci.loci_backend.common.validation.domain;

import java.util.Map;

/*
 * Define contact validation exception
 * Each specific exception must return it type as enum via type()
 * Exception perform on field - reason basis
 */
public abstract class AssertionException extends RuntimeException {

  private final String field;
  protected AssertionException(String field, String message){
    super(message);
    this.field = field;
  }
  public abstract AssertionErrorType type();
  public String field(){
    return this.field;
  }
  public Map<String, String> parameters(){
    return Map.of();
  }

}
