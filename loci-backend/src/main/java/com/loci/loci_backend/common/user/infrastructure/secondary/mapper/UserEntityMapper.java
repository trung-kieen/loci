package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class UserEntityMapper implements DomainEntityMapper<User, UserEntity> {

  private final MapStructUserEntityMapper mapstruct;
  private final AuthorityEntityMapper authorityEntityMapper;

  public User toDomain(UserEntity userEntity) {
    return mapstruct.toDomain(userEntity);

    // return UserBuilder.user()
    // .userPublicId(new PublicId(userEntity.getPublicId()))
    // .dbId(new UserDBId(userEntity.getId()))
    // .email(new UserEmail(userEntity.getEmail()))
    // .firstname(new UserFirstname(userEntity.getFirstname()))
    // .lastname(new UserLastname(userEntity.getLastname()))
    // .username(new Username(userEntity.getUsername()))
    // .profilePicture(new UserImageUrl(userEntity.getProfilePicture()))
    // .createdDate(userEntity.getCreatedDate())
    // .lastModifiedDate(userEntity.getLastModifiedDate())
    // .bio(new ProfileBio(userEntity.getBio()))
    // .lastActive(userEntity.getLastActive())
    // .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
    // .build();

  }

  public UserEntity from(User user) {
    return mapstruct.from(user);

    // return UserEntityBuilder.userEntity()
    // .publicId(NullSafe.getIfPresent(user.getUserPublicId()))
    // .id(NullSafe.getIfPresent(user.getDbId()))
    // .email(user.getEmail().value())
    // .firstname(user.getFirstname().value())
    // .lastname(user.getLastname().value())
    // .username(user.getUsername().value())
    // .profilePicture(NullSafe.getIfPresent(user.getProfilePicture()))
    // .bio(NullSafe.getIfPresent(user.getBio()))
    // .lastActive(user.getLastActive())
    // .authorities(authorityEntityMapper.from(user.getAuthorities()))
    // .build();
  }

}
