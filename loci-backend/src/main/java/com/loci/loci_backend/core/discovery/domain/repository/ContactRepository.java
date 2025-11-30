package com.loci.loci_backend.core.discovery.domain.repository;

import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepository {

  Page<Contact> searchContacts(ContactSearchCriteria criteria, Pageable pageable);
}
