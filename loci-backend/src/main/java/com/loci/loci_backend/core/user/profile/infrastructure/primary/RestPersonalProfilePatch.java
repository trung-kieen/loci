package com.loci.loci_backend.core.user.profile.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.user.profile.domain.aggregate.Fullname;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfileChanges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPersonalProfilePatch {
  private String firstname;
  private String lastname;

  // Unchange field
  // private String username;
  // private String emailAddress;

  private String profilePictureUrl;
  private RestProfilePrivacy privacy;

  public static PersonalProfileChanges toDomain(RestPersonalProfilePatch patch) {
    var builder = PersonalProfileChanges.builder();
    UserFirstname firstname = NullSafe.getIfPresent(patch.firstname, (f) -> new UserFirstname(f));
    UserLastname lastname = NullSafe.getIfPresent(patch.lastname, (l) -> new UserLastname(l));

    builder.fullname(Fullname.from(firstname, lastname));
    // .username(new Username(patch.username))
    // .email(new UserEmail(patch.emailAddress))
    builder.imageUrl(NullSafe.getIfPresent(patch.profilePictureUrl, (p) -> new UserImageUrl(p)));
    builder.privacySetting(NullSafe.getIfPresent(patch.privacy, p -> RestProfilePrivacy.toDomain(p)));
    return builder.build();
  }

}
