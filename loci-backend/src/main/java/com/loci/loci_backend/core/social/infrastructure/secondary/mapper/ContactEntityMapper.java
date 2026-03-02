package com.loci.loci_backend.core.social.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ContactEntityMapper {
  private final MapStructContactEntityMapper mapstruct;

  public ContactConnection toDomain(ContactEntity entity) {
    return mapstruct.toDomain(entity);
  }
  // public Page<ContactConnection> toDomain(Page<ContactEntity> entity) {
  //   return Pages.map(entity, this::toDomain);
  // }

  public ContactRequest toDomain(ContactRequestEntity entity) {
    return mapstruct.toDomain(entity);
  }

  public ContactRequestEntity from(ContactRequest contactRequest) {
    return mapstruct.from(contactRequest);
  }

  public Page<ContactRequest> toDomain(Page<ContactRequestEntity> entities) {
    return Pages.map(entities, this::toDomain);
    // return entities.map(this::toDomain);
  }

  public ContactEntity from(ContactConnection contact) {
    return mapstruct.from(contact);
  }

}
