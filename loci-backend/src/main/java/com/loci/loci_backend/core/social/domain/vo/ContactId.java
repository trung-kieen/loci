
package com.loci.loci_backend.core.social.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;

public record ContactId(Long value) implements ValueObject<Long> {

  public ContactId {
    Assert.notNull("contact id ", value);
  }
}
