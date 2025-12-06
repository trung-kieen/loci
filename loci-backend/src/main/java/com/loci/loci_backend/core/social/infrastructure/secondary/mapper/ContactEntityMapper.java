package com.loci.loci_backend.core.social.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.aggregate.ContactBuilder;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.vo.ContactId;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactEntityMapper {
  private final MapStructContactEntityMapper mapstruct;

  public Contact toDomain(ContactEntity entity) {
    return ContactBuilder.contact().contactId(new ContactId(entity.getId()))
        .owningUserId(new UserDBId(entity.getOwningUser().getId()))
        .contactUserId(new UserDBId(entity.getContactUser().getId()))
        .blockedByUserId(NullSafe.getIfPresent(entity.getBlockedBy(), (userEntity) -> new UserDBId(userEntity.getId())))
        .build();
  }

  public ContactRequest toDomain(ContactRequestEntity entity) {
    return mapstruct.toDomain(entity);
  }

  public ContactRequestEntity from(ContactRequest contactRequest) {
    return mapstruct.from(contactRequest);
  }
  public Page<ContactRequest> toDomain(Page<ContactRequestEntity> entities){
    return entities.map(this::toDomain);
  }

  public ContactEntity from(Contact contact) {
    return mapstruct.from(contact);
  }

}
