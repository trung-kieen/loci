package com.loci.loci_backend.common.user.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.validation.domain.Assert;

import lombok.Builder;
import lombok.Data;

/**
 * Authority
 */
@Builder
@Data
public class Authority {

  private AuthorityName name;
  public Authority (AuthorityName name){
    Assert.notNull("name", name);
    this.name = name;
  }


}
