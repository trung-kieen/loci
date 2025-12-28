package com.loci.loci_backend.core.groups.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructGroupEntityMapper {

  @Mapping(target = "id", ignore = true) // create so make null for generate id
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public GroupEntity from(CreateGroupProfileRequest request);

  @Mapping(source = "groupId", target = "id")
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public GroupEntity from(GroupProfile domainObject);

  @Mapping(source = "id", target = "groupId")
  public GroupProfile toDomain(GroupEntity entity);

}
