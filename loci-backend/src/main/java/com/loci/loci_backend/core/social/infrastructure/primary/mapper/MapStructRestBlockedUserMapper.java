package com.loci.loci_backend.core.social.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructRestBlockedUserMapper {

  @Mapping(source = "userPublicId", target = "userId")
  @Mapping(source = "profilePicture", target = "profilePictureUrl")
  public RestBlockedUser from(User domain);

}
