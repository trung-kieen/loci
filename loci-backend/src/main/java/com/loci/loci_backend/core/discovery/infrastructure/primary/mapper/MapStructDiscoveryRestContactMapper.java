package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestSearchContact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructDiscoveryRestContactMapper {
  @Mapping(source = "userEmail", target = "email")
  @Mapping(source = "publicId", target = "userId")
  public RestSearchContact from(SearchContact contact);

}
