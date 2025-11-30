package com.loci.loci_backend.core.discovery.domain.aggregate;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.identity.domain.aggregate.Fullname;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contact {
  private UserPublicId publicId;

  private Fullname fullname;

  private Username username;

  private UserEmail userEmail;

  private UserImageUrl imageUrl;

  private FriendshipStatus friendshipStatus;
}
