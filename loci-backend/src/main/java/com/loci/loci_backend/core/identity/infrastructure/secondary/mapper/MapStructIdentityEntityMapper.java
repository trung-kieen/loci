package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.util.ValueObjectTypeConverter;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructIdentityEntityMapper {

  @Mapping(source = "profilePicture", target = "imageUrl")
  @Mapping(source = "email", target = "userEmail")
  @Mapping(source = "id", target = "dbId")
  public UserSummary toUserSummary(UserEntity entity);


}
