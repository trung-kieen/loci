package com.loci.loci_backend.core.user.domain.profile.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PublicProfile {
  private Username username;
  private Fullname fullname;
  private UserEmail email;
  private UserImageUrl imageUrl;
  private Instant createdDate;


}
