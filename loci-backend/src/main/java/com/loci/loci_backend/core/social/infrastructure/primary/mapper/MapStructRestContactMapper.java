package com.loci.loci_backend.core.social.infrastructure.primary.mapper;

import com.loci.loci_backend.common.util.ValueObjectTypeConverter;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructRestContactMapper {

  @Mapping(source = "publicId", target = "userId")
  @Mapping(source = "userEmail", target = "email")
  @Mapping(target = "friendshipStatus", ignore = true)
  public RestContactRequest toRestContactRequest(UserSummary user);

}
