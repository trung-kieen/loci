package com.loci.loci_backend.core.discovery.infrastructure.primary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfile;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContactProfile;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContactProfileList;

import org.springframework.data.domain.Page;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestContactProfileMapper {
  private final MapStructRestContactProfileMapper mapstruct;

  public RestContactProfileList from(ContactProfileList discoveryContacts) {
    Page<RestContactProfile> contacts = Pages.map(discoveryContacts.getContacts(), this::from);
    return new RestContactProfileList(contacts);
  }

  public RestContactProfile from(ContactProfile domain) {
    return mapstruct.from(domain);
  }

}
