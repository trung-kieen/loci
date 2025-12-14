package com.loci.loci_backend.common.migration.domain.aggregate;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class KeycloakUser {
  private Username  username;
  private UserEmail email;
  private UserFirstname firstName;
  private UserLastname lastName;
  // private UserPassword password;
  private final boolean enabled;
}
