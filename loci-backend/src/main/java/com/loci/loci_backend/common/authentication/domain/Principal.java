package com.loci.loci_backend.common.authentication.domain;

import com.loci.loci_backend.common.authentication.infrastructure.primary.resolver.WebMvcConfiguration;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;

/**
 * Authenticated user in current request
 * @see WebMvcConfiguration
 */
public interface Principal {
  Username getUsername();

  UserEmail getUserEmail();
}
