package com.loci.loci_backend.core.social.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestBuilder;
import com.loci.loci_backend.core.social.domain.repository.ContactRequestRepository;
import com.loci.loci_backend.core.social.domain.vo.ContactRequestId;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.FriendRequestStatus;
import com.loci.loci_backend.core.social.infrastructure.secondary.mapper.ContactEntityMapper;
import com.loci.loci_backend.core.social.infrastructure.secondary.specification.JpaContactRequestSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataContactRequestRepository implements ContactRequestRepository {
  private final JpaContactRequestRepository repository;
  private final ContactEntityMapper mapper;

  @Override
  public boolean existsPendingRequest(User a, User b) {
    Specification<ContactRequestEntity> spec = JpaContactRequestSpecification.searchContactRequest(a.getDbId().value(),
        b.getDbId().value());
    List<ContactRequestEntity> requests = repository.findAll(spec);
    for (ContactRequestEntity r : requests) {
      if (r.getStatus() == FriendRequestStatus.PENDING) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ContactRequest save(ContactRequest contactRequest) {
    ContactRequestEntity request = mapper.from(contactRequest);
    return mapper.toDomain(repository.save(request));
  }

  @Override
  public Page<ContactRequest> getAllPendingByReceiver(UserDBId dbId, Pageable pageable) {
    Long requestUserId = dbId.value();
    Specification<ContactRequestEntity> spec = JpaContactRequestSpecification.withReceiverId(requestUserId);
    Page<ContactRequestEntity> contactEntities = repository.findAll(spec, pageable);
    return mapper.toDomain(contactEntities);
  }

  @Override
  public Optional<ContactRequest> getPendingRequest(UserDBId a, UserDBId b) {
    return repository.findConnection(a.value(), b.value()).map(mapper::toDomain);
  }

  @Override
  public Optional<ContactRequest> getByPublicId(PublicId publicId) {
    return repository.findByPublicId(publicId.value()).map(mapper::toDomain);
  }

}
