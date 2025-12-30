package com.loci.loci_backend.core.groups.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class GroupEntityMapper implements DomainEntityMapper<GroupProfile, GroupEntity> {
  private final MapStructGroupEntityMapper mapper;

  public GroupEntity from(CreateGroupProfileRequest request) {
    return mapper.from(request);
  }


  @Override
  public GroupEntity from(GroupProfile domainObject) {
    return mapper.from(domainObject);
  }


  @Override
  public GroupProfile toDomain(GroupEntity entity) {
    return mapper.toDomain(entity);
  }


}
