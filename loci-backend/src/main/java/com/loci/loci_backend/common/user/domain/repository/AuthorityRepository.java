package com.loci.loci_backend.common.user.domain.repository;

import java.util.Collection;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.Authority;


public interface AuthorityRepository {


  public Authority save(Authority authority);
  public boolean exists(Authority authority);
  public Set<Authority> saveAll(Collection<Authority> authorities);


}
