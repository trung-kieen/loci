package com.loci.loci_backend.core.identity.domain.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

public interface IdentityUserRepository {
  public List<UserSummary> getUserSummary(List<UserDBId> ids) ;
}
