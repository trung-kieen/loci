package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContact;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestContactMapper {

  public Page<RestContact> from(Page<Contact> searchActiveUsers) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'from'");
  }

}
