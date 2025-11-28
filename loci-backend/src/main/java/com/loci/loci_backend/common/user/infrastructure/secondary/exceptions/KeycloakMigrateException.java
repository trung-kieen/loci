package com.loci.loci_backend.common.user.infrastructure.secondary.exceptions;

public class KeycloakMigrateException extends RuntimeException {

  public KeycloakMigrateException (String message){
    super(message);
  }

}
