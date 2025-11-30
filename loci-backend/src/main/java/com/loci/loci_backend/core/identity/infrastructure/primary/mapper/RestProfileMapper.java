package com.loci.loci_backend.core.identity.infrastructure.primary.mapper;

import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.common.util.TimeFormatter;
import com.loci.loci_backend.core.identity.domain.aggregate.Fullname;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfilePrivacy;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestProfileMapper {

  public RestPersonalProfile from(PersonalProfile personalProfile) {
    return RestPersonalProfile.builder()
        .firstname(personalProfile.getFullname().getFirstname().value())
        .lastname(personalProfile.getFullname().getLastname().value())
        .username(personalProfile.getUsername().get())
        .emailAddress(personalProfile.getEmail().value())
        .profilePictureUrl(
            personalProfile.getImageUrl().valueOrDefault())
        .privacy(NullSafe.getIfPresent(personalProfile.getPrivacySetting(), (p) -> RestProfilePrivacy.from(p)))
        .build();
  }

  public Page<RestPublicProfile> from(Page<PublicProfile> profile) {
    return profile.map(this::from);
  }

  public PersonalProfileChanges toDomain(RestPersonalProfilePatch patch) {
    var builder = PersonalProfileChanges.builder();
    UserFirstname firstname = NullSafe.getIfPresent(patch.getFirstname(), (f) -> new UserFirstname(f));
    UserLastname lastname = NullSafe.getIfPresent(patch.getFirstname(), (l) -> new UserLastname(l));

    builder.fullname(Fullname.from(firstname, lastname));
    // .username(new Username(patch.username))
    // .email(new UserEmail(patch.emailAddress))
    builder.imageUrl(NullSafe.getIfPresent(patch.getProfilePictureUrl(), (p) -> new UserImageUrl(p)));
    builder.privacySetting(NullSafe.getIfPresent(patch.getPrivacy(), p -> RestProfilePrivacy.toDomain(p)));
    return builder.build();
  }



  public RestPublicProfile from(PublicProfile profile) {
    return RestPublicProfile.builder()
        .publicId(profile.getPublicId().value().toString())
        .fullname(profile.getFullname().value())
        .username(profile.getUsername().get())
        .emailAddress(profile.getEmail().value())
        .createdAt(profile.getCreatedDate())
        .memberSince(TimeFormatter.timeAgo(profile.getCreatedDate()))
        .profilePictureUrl(
            profile.getImageUrl().valueOrDefault())
        .build();
  }
}
