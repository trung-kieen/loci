package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructUserPresenceEntityMapper {
  public UserPresence toDomain(UserPresenceEntity entity);

}
