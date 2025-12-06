package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.mapper.ContactEntityMapper;
import com.loci.loci_backend.core.social.infrastructure.secondary.specification.JpaContactSpecification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataContactRepository implements ContactRepository {
  private final JpaContactRepository repository;
  private final ContactEntityMapper mapper;

  @Override
  public Optional<Contact> searchContact(UserDBId a, UserDBId b) {
    Specification<ContactEntity> contactSpec = JpaContactSpecification.searchContact(a.value(), b.value());
    List<ContactEntity> contacts = repository.findAll(contactSpec);
    if (contacts.isEmpty()) {
      return Optional.empty();
    }
    try {
      ContactEntity entity = contacts.get(0);
      return Optional.ofNullable(mapper.toDomain(entity));
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<Contact> searchContact(User a, User b) {
    return searchContact(a.getDbId(), b.getDbId());
  }

  @Override
  public boolean existsContactConnection(User a, User b) {
    Optional<Contact> contactOpt = searchContact(a.getDbId(), b.getDbId());
    return contactOpt.isPresent();
  }

  @Override
  public Contact save(Contact contact) {
    ContactEntity entity = mapper.from(contact);
    return mapper.toDomain(repository.save(entity));
  }

  @Override
  public void removeContact(UserDBId a, UserDBId b) {
    ContactEntity connection = repository.findConnection(a.value(), b.value())
        .orElseThrow(() -> new EntityNotFoundException("Not found connection between users"));
    repository.delete(connection);
  }

}
