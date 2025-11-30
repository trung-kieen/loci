package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.domain.repository.ContactRepository;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper.ContactEntityMapper;
import com.loci.loci_backend.core.identity.infrastructure.secondary.persistence.UserSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataContactRepository implements ContactRepository {
  private final JpaUserRepository userRepository;
  private final ContactEntityMapper contactMapper;

  @Override
  public Page<Contact> searchContacts(ContactSearchCriteria criteria, Pageable pageable) {
    String keyword = criteria.getKeyword();
    Specification<UserEntity> spec = UserSpecifications.searchActiveUsers(keyword);
    Page<UserEntity> entityPage = userRepository.findAll(spec, pageable);
    return contactMapper.toDomain(entityPage);
  }
}
