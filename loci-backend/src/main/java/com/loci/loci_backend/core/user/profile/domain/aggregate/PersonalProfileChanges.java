package com.loci.loci_backend.core.user.profile.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalProfileChanges {

  private Fullname fullname;

  // Not apply change this
  // private Username username;

  // Not apply change this
  // private UserEmail email;

  private UserImageUrl imageUrl;

  private PrivacySetting privacySetting;

}
