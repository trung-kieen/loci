package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.aggregate.UserBuilder;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntityBuilder;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySettingBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfileBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {
  private final AuthorityEntityMapper authorityEntityMapper;

  public PublicProfile toPublicProfile(UserEntity userEntity) {
    return PublicProfileBuilder.publicProfile()
        .publicId(new UserPublicId(userEntity.getPublicId()))
        .email(new UserEmail(userEntity.getEmail()))
        .fullname(
            UserFullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .username(new Username(userEntity.getUsername()))
        .imageUrl(new UserImageUrl(userEntity.getProfilePicture()))
        .createdDate(userEntity.getCreatedDate())
        .build();
  }

  public Page<PublicProfile> toDomain(Page<UserEntity> userPage) {
    Page<PublicProfile> profilePage = userPage.map(this::toPublicProfile);

    return profilePage;

  }

  public User toDomain(UserEntity userEntity) {

    return UserBuilder.user()
        .userPublicId(new UserPublicId(userEntity.getPublicId()))
        .dbId(userEntity.getId())
        .email(new UserEmail(userEntity.getEmail()))
        .firstname(new UserFirstname(userEntity.getFirstname()))
        .lastname(new UserLastname(userEntity.getLastname()))
        .username(new Username(userEntity.getUsername()))
        .profilePicture(new UserImageUrl(userEntity.getProfilePicture()))
        .createdDate(userEntity.getCreatedDate())
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .bio(new ProfileBio(userEntity.getBio()))
        .lastActive(userEntity.getLastActive())
        .privacySetting(userEntity.getPrivacySetting())
        .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
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
        .userPublicId(new UserPublicId(userEntity.getPublicId()))
        .build();
  }

  public UserEntity from(PersonalProfile profile) {

    return UserEntityBuilder.userEntity()
        // .publicId(NullSafe.getIfPresent(profile.getUserPublicId()))
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

  public UserEntity from(User user) {

    PrivacySetting privacySettings = NullSafe.getIfPresent(user.getPrivacySetting(), ps -> {
      return PrivacySettingBuilder.privacySetting()
          .lastSeenSetting(ps.getLastSeenSetting())
          .friendRequestSetting(ps.getFriendRequestSetting())
          .profileVisibility(ps.getProfileVisibility())
          .build();
    });

    return UserEntityBuilder.userEntity()
        .publicId(NullSafe.getIfPresent(user.getUserPublicId()))
        .id(user.getDbId())
        .email(user.getEmail().value())
        .firstname(user.getFirstname().value())
        .lastname(user.getLastname().value())
        .username(user.getUsername().value())
        .profilePicture(NullSafe.getIfPresent(user.getProfilePicture()))
        .bio(NullSafe.getIfPresent(user.getBio()))
        .lastActive(user.getLastActive())
        .lastSeenSetting(NullSafe.getIfPresent(privacySettings.getLastSeenSetting()))
        .friendRequestSetting(NullSafe.getIfPresent(privacySettings.getFriendRequestSetting()))
        .profileVisibility(NullSafe.getIfPresent(privacySettings.getProfileVisibility()))
        .authorities(authorityEntityMapper.from(user.getAuthorities()))
        .build();

  }

}
