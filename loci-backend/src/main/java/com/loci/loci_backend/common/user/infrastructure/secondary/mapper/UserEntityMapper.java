package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.AuthorityEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity.UserEntityBuilder;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.Fullname;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {
  private final AuthorityEntityMapper authorityEntityMapper;

  public PublicProfile toPublicProfile(UserEntity userEntity) {
    return PublicProfile.builder()
        .publicId(new UserPublicId(userEntity.getPublicId()))
        .username(new Username(userEntity.getUsername()))
        .fullname(
            Fullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .email(new UserEmail(userEntity.getEmail()))
        .imageUrl(new UserImageUrl(userEntity.getImageURL()))
        .createdDate(userEntity.getCreatedDate())
        .build();
  }

  public Page<PublicProfile> toDomain(Page<UserEntity> userPage) {
    Page<PublicProfile> profilePage = userPage.map(this::toPublicProfile);

    return profilePage;

  }

  public User toDomain(UserEntity userEntity) {
    return User.builder()
        .email(new UserEmail(userEntity.getEmail()))
        .firstname(new UserFirstname(userEntity.getFirstname()))
        .lastname(new UserLastname(userEntity.getLastname()))
        .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .privacySetting(userEntity.getPrivacySetting())
        .dbId(userEntity.getId())
        .build();
  }

  public PersonalProfile toPersonalProfile(UserEntity userEntity) {
    return PersonalProfile.builder()
        .fullname(
            Fullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .email(new UserEmail(userEntity.getEmail()))
        .username(new Username(userEntity.getUsername()))
        .userPublicId(new UserPublicId(userEntity.getPublicId()))
        .imageUrl(new UserImageUrl(userEntity.getImageURL()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
        .privacySetting(userEntity.getPrivacySetting())
        .dbId(userEntity.getId())
        .build();
  }

  public UserEntity from(PersonalProfile profile) {
    UserEntityBuilder userEntityBuilder = UserEntity.builder();

    NullSafe.applyIfPresent(profile::getImageUrl, i -> userEntityBuilder.imageURL(i.value()));

    NullSafe.applyIfPresent(profile::getUserPublicId, i -> userEntityBuilder.publicId(i.value()));

    return userEntityBuilder
        .authorities(authorityEntityMapper.from(profile.getAuthorities()))
        .email(profile.getEmail().value())
        .firstname(profile.getFullname().getFirstname().value())
        .lastname(profile.getFullname().getLastname().value())
        .lastSeen(profile.getLastSeen())
        .lastSeenSetting(profile.getPrivacySetting().getLastSeenSetting().value())
        .friendRequestSetting(profile.getPrivacySetting().getFriendRequestSetting().value())
        .profileVisibility(profile.getPrivacySetting().getProfileVisibility().value())
        .id(profile.getDbId())
        .build();
  }

  public UserEntity from(User user) {
    UserEntity.UserEntityBuilder builder = UserEntity.builder()
        .authorities(authorityEntityMapper.from(user.getAuthorities()))
        .email(user.getEmail().value())
        .firstname(user.getFirstname().value())
        .lastname(user.getLastname().value())
        .lastSeen(user.getLastSeen())
        .id(user.getDbId());
    NullSafe.applyIfPresent(user::getPrivacySetting, ps -> {
      NullSafe.applyIfPresent(ps::getLastSeenSetting, lss -> builder.lastSeenSetting(lss.value()));
      NullSafe.applyIfPresent(ps::getFriendRequestSetting, frs -> builder.friendRequestSetting(frs.value()));
      NullSafe.applyIfPresent(ps::getProfileVisibility, pv -> builder.profileVisibility(pv.value()));
    });

    NullSafe.applyIfPresent(user::getImageUrl, iu -> builder.imageURL(iu.value()));
    NullSafe.applyIfPresent(user::getUserPublicId, upi -> builder.publicId(upi.value()));

    // EntityMapper.applyIfPresent(user::getUserAddress, addr -> {
    // builder.addressCity(addr.city());
    // builder.addressCountry(addr.country());
    // builder.addressZipCode(addr.zipCode());
    // builder.addressStreet(addr.street());
    // });
    return builder.build();
  }

}
