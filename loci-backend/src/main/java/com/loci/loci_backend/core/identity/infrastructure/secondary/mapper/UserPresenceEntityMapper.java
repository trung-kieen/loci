package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class UserPresenceEntityMapper {
  private final MapStructUserPresenceEntityMapper mapstruct;

  public UserPresence toDomain(UserPresenceEntity entity) {
    return mapstruct.toDomain(entity);
  }

}
