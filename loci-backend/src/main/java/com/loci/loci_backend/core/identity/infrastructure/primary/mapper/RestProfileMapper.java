package com.loci.loci_backend.core.identity.infrastructure.primary.mapper;

import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.common.util.TimeFormatter;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChangesBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfileBuilder;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfilePrivacy;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfileBuilder;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestProfileMapper {

  public RestPersonalProfile from(PersonalProfile personalProfile) {
    return RestPersonalProfileBuilder.restPersonalProfile()
        .emailAddress(personalProfile.getEmail().value())
        .firstname(personalProfile.getFullname().getFirstname().value())
        .lastname(personalProfile.getFullname().getLastname().value())
        .username(personalProfile.getUsername().value())
        .profilePictureUrl(
            personalProfile.getImageUrl().valueOrDefault())
        .privacy(NullSafe.getIfPresent(personalProfile.getPrivacySetting(), (p) -> RestProfilePrivacy.from(p)))
        .build();
  }

  public Page<RestPublicProfile> from(Page<PublicProfile> profile) {
    return profile.map(this::from);
  }

  public PersonalProfileChanges toDomain(RestPersonalProfilePatch patch) {
    UserFirstname firstname = NullSafe.constructOrNull(UserFirstname.class, patch.getFirstname());
    UserLastname lastname = NullSafe.constructOrNull(UserLastname.class, patch.getLastname());
    return PersonalProfileChangesBuilder.personalProfileChanges()
        .fullname(UserFullname.from(firstname, lastname))
        .bio(NullSafe.constructOrNull(ProfileBio.class, patch.getBio()))
        .imageUrl(NullSafe.constructOrNull(UserImageUrl.class, patch.getProfilePictureUrl()))
        .privacySetting(NullSafe.getIfPresent(patch.getPrivacy(), p -> RestProfilePrivacy.toDomain(p)))
        .build();
  }

  public RestPublicProfile from(PublicProfile profile) {
    return RestPublicProfileBuilder.restPublicProfile()
        .publicId(profile.getPublicId().value().toString())
        .emailAddress(profile.getEmail().value())
        .fullname(profile.getFullname().value())
        .username(profile.getUsername().get())
        .profilePictureUrl(
            profile.getImageUrl().valueOrDefault())
        .memberSince(TimeFormatter.timeAgo(profile.getCreatedDate()))
        .createdAt(profile.getCreatedDate())
        .build();
  }
}
