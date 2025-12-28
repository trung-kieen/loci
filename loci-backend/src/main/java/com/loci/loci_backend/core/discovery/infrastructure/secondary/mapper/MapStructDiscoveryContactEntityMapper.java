package com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructDiscoveryContactEntityMapper {

  @Mapping(source = "user.profilePicture", target = "imageUrl")
  @Mapping(source = "user.email", target = "userEmail")
  @Mapping(source = "friendshipStatus", target = "friendshipStatus")
  public SearchContact  toDomain(UserEntity user, FriendshipStatus friendshipStatus) ;

}
