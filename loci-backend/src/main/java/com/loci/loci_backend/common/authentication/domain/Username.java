package com.loci.loci_backend.common.authentication.domain;

import java.util.Optional;

import com.loci.loci_backend.common.validation.domain.Assert;

import io.micrometer.common.util.StringUtils;

public record Username(String username) {
  public Username {
    Assert.field("username", username).notNull().notBlank();

  }
  public String get(){
    return username;
  }
  // Use to convert the dto to domain
  public static Optional<Username> of(String username){
    return Optional.ofNullable(username).filter(StringUtils::isNotBlank).map(Username::new);
  }
}
