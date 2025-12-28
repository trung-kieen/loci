package com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscoveryContactEntityMapper {
  private final MapStructDiscoveryContactEntityMapper mapstruct;

  public Page<SearchContact> toDomain(Page<UserEntity> entityPage) {
    return entityPage.map(this::toDomain);
  }

  public SearchContact toDomain(UserEntity entity, FriendshipStatus friendshipStatus) {
    return mapstruct.toDomain(entity, friendshipStatus);
  }

  public SearchContact toDomain(UserEntity entity) {
    return toDomain(entity, FriendshipStatus.ofDefault());
    // UserFullname fullname = UserFullname.builder()
    // .firstname(new UserFirstname(entity.getFirstname()))
    // .lastname(new UserLastname(entity.getLastname()))
    // .build();
    // return SearchContactBuilder.searchContact()
    // .publicId(new PublicId(entity.getPublicId()))
    // .fullname(fullname)
    // .username(new Username(entity.getUsername()))
    // .userEmail(new UserEmail(entity.getEmail()))
    // .imageUrl(new UserImageUrl(entity.getProfilePicture()))
    // // TODO: fetch friendship status
    // .friendshipStatus(null)
    // .build();
  }

}
