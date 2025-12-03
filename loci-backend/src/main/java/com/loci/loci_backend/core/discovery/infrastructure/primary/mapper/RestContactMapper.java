package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContact;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestContactMapper {
  private final MapStructRestContactMapper contactMapper;

  public RestContact from(Contact contact) {
    return contactMapper.from(contact);
  }

  public Page<RestContact> from(Page<Contact> contactPage) {
    return contactPage.map(this::from);
  }

}
