package com.loci.loci_backend.core.user.domain.profile.aggregate;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.UserAddress;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.user.infrastructure.primary.RestProfilePrivacy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalProfile {


  private Fullname fullname;

  private UserEmail email;

  private Username username;

  private UserPublicId userPublicId;

  private UserImageUrl imageUrl;

  private Instant lastModifiedDate;

  private Instant createdDate;

  private Set<Authority> authorities;

  private Long dbId;

  private Instant lastSeen;

  // TODO:
  // private ProfilePrivacy privacy;

}
