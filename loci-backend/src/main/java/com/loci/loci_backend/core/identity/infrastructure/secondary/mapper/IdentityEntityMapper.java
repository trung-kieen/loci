package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import java.util.List;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntityBuilder;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.AuthorityEntityMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfileBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityEntityMapper {
  private final AuthorityEntityMapper authorityEntityMapper;
  private final MapStructIdentityEntityMapper mapstruct;

  public PublicProfile toPublicProfile(UserEntity userEntity) {
    return PublicProfileBuilder.publicProfile()
        .publicId(new PublicId(userEntity.getPublicId()))
        .userDbId(new UserDBId(userEntity.getId()))
        .email(new UserEmail(userEntity.getEmail()))
        .fullname(
            UserFullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .username(new Username(userEntity.getUsername()))
        .imageUrl(new UserImageUrl(userEntity.getProfilePicture()))
        .createdDate(userEntity.getCreatedDate())
        .connectionStatus(null)
        .build();
  }

  public PersonalProfile toPersonalProfile(UserEntity userEntity) {
    return PersonalProfileBuilder.personalProfile()
        .dbId(userEntity.getId())
        .email(new UserEmail(userEntity.getEmail()))
        .fullname(
            UserFullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .username(new Username(userEntity.getUsername()))
        .imageUrl(new UserImageUrl(userEntity.getProfilePicture()))
        .bio(new ProfileBio(userEntity.getBio()))
        .createdDate(userEntity.getCreatedDate())
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .lastActive(userEntity.getLastActive())
        .privacySetting(userEntity.getPrivacySetting())
        .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
        .userPublicId(new PublicId(userEntity.getPublicId()))
        .build();
  }

  public UserEntity from(PersonalProfile profile) {

    return UserEntityBuilder.userEntity()
        // .publicId(NullSafe.getIfPresent(profile.getPublicId()))
        .publicId(profile.getUserPublicId().value())
        .id(profile.getDbId())
        .email(profile.getEmail().value())
        .firstname(profile.getFullname().getFirstname().value())
        .lastname(profile.getFullname().getLastname().value())
        .username(profile.getUsername().value())
        // .profilePicture(NullSafe.getIfPresent(profile.getImageUrl()))
        .profilePicture(profile.getImageUrl().value())
        // .bio(NullSafe.getIfPresent(profile.getBio()))
        .bio(profile.getBio().value())
        .lastActive(profile.getLastActive())
        .lastSeenSetting(profile.getPrivacySetting().getLastSeenSetting().value())
        .friendRequestSetting(profile.getPrivacySetting().getFriendRequestSetting().value())
        .profileVisibility(profile.getPrivacySetting().getProfileVisibility().value())
        .authorities(authorityEntityMapper.from(profile.getAuthorities()))
        .build();
  }

  public UserSummary toUserSummary(UserEntity entity) {
    return mapstruct.toUserSummary(entity);
  }

  public List<UserSummary> toUserSummary(List<UserEntity> userEntities) {
    return userEntities.stream().map(this::toUserSummary).toList();
  }

  public Page<PublicProfile> toDomain(Page<UserEntity> userPage) {
    Page<PublicProfile> profilePage = userPage.map(this::toPublicProfile);

    return profilePage;

  }
}
