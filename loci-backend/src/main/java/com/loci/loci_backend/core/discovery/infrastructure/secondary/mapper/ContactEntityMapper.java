package com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactEntityMapper {

  public Page<Contact> toDomain(Page<UserEntity> entityPage) {
    return entityPage.map(this::toDomain);
  }

  public Contact toDomain(UserEntity entityPage) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toDomain'");
  }

}
