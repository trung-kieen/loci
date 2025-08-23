package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.validation.domain.Assert;

/**
 * UserAddress
 */
public record UserAddress(String street, String city, String zipCode, String country) {
  public UserAddress {
    Assert.field("street", street).notNull();
    Assert.field("city", city).notNull();
    Assert.field("zipCode", zipCode).notNull();
    Assert.field("country", country).notNull();
  }
}
