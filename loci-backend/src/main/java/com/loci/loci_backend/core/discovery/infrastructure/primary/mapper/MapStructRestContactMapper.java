package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.common.util.ValueObjectTypeConverter;
import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContact;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)
public interface MapStructRestContactMapper {
  public RestContact from(Contact contact);

}
