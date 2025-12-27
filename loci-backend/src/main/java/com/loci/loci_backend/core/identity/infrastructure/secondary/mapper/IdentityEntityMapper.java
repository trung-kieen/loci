package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import java.util.List;

import com.loci.loci_backend.common.mapper.DomainEntityMapper;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.AuthorityEntityMapper;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityEntityMapper implements DomainEntityMapper<UserSettings, UserSettingsEntity> {
  private final MapStructIdentityEntityMapper mapstruct;

  public PublicProfile toPublicProfile(UserEntity userEntity, FriendshipStatus connectionStatus) {
    PublicProfile profile = mapstruct.toPublicProfile(userEntity);
    if (connectionStatus != null) {
      profile.setConnectionStatus(connectionStatus);
    }
    return profile;

  }

  public PublicProfile toPublicProfile(UserEntity userEntity) {
    return this.toPublicProfile(userEntity, null);
    // return PublicProfileBuilder.publicProfile()
    // .publicId(new PublicId(userEntity.getPublicId()))
    // .userDBId(new UserDBId(userEntity.getId()))
    // .email(new UserEmail(userEntity.getEmail()))
    // .fullname(
    // UserFullname.from(new UserFirstname(userEntity.getFirstname()), new
    // UserLastname(userEntity.getLastname())))
    // .username(new Username(userEntity.getUsername()))
    // .imageUrl(new UserImageUrl(userEntity.getProfilePicture()))
    // .createdDate(userEntity.getCreatedDate())
    // .connectionStatus(null)
    // .build();
  }

  public PersonalProfile toPersonalProfile(UserEntity userEntity) {
    return mapstruct.toPersonalProfile(userEntity);
  }

  public UserEntity from(PersonalProfile profile) {
    return mapstruct.from(profile);

    // return UserEntityBuilder.userEntity()
    // .publicId(profile.getUserPublicId().value())
    // .id(profile.getDbId())
    // .email(profile.getEmail().value())
    // .firstname(profile.getFullname().getFirstname().value())
    // .lastname(profile.getFullname().getLastname().value())
    // .username(profile.getUsername().value())
    // .profilePicture(profile.getImageUrl().value())
    // .bio(profile.getBio().value())
    // .lastActive(profile.getLastActive())
    // .authorities(authorityEntityMapper.from(profile.getAuthorities()))
    // .build();
  }

  public UserSummary toUserSummary(UserEntity entity) {
    return mapstruct.toUserSummary(entity);
  }

  public List<UserSummary> toUserSummary(List<UserEntity> userEntities) {
    return userEntities.stream().map(this::toUserSummary).toList();
  }

  @Override
  public UserSettings toDomain(UserSettingsEntity settings) {
    return mapstruct.toDomain(settings);
  }

  @Override
  public UserSettingsEntity from(UserSettings domainObjectt) {
    return mapstruct.from(domainObjectt);
  }
}
