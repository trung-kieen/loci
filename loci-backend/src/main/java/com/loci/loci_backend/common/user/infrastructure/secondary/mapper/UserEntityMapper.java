package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.mapper.DomainEntityMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.aggregate.UserBuilder;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntityBuilder;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEntityMapper implements DomainEntityMapper<User, UserEntity> {

  private final AuthorityEntityMapper authorityEntityMapper;

  public User toDomain(UserEntity userEntity) {

    return UserBuilder.user()
        .userPublicId(new PublicId(userEntity.getPublicId()))
        .dbId(new UserDBId(userEntity.getId()))
        .email(new UserEmail(userEntity.getEmail()))
        .firstname(new UserFirstname(userEntity.getFirstname()))
        .lastname(new UserLastname(userEntity.getLastname()))
        .username(new Username(userEntity.getUsername()))
        .profilePicture(new UserImageUrl(userEntity.getProfilePicture()))
        .createdDate(userEntity.getCreatedDate())
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .bio(new ProfileBio(userEntity.getBio()))
        .lastActive(userEntity.getLastActive())
        .authorities(authorityEntityMapper.toDomain(userEntity.getAuthorities()))
        .build();

  }

  public UserEntity from(User user) {

    return UserEntityBuilder.userEntity()
        .publicId(NullSafe.getIfPresent(user.getUserPublicId()))
        .id(NullSafe.getIfPresent(user.getDbId()))
        .email(user.getEmail().value())
        .firstname(user.getFirstname().value())
        .lastname(user.getLastname().value())
        .username(user.getUsername().value())
        .profilePicture(NullSafe.getIfPresent(user.getProfilePicture()))
        .bio(NullSafe.getIfPresent(user.getBio()))
        .lastActive(user.getLastActive())
        .authorities(authorityEntityMapper.from(user.getAuthorities()))
        .build();

  }

}
