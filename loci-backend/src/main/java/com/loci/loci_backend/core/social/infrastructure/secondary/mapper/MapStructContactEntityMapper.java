package com.loci.loci_backend.core.social.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ValueObjectTypeConverter.class)

public interface MapStructContactEntityMapper {

  @Mapping(target = "receiver", ignore = true)
  @Mapping(target = "requester", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public ContactRequestEntity from(ContactRequest request);

  public ContactRequest toDomain(ContactRequestEntity entity);


  @Mapping(source = "contactId", target = "id")
  public ContactEntity from(Contact contact) ;
}
