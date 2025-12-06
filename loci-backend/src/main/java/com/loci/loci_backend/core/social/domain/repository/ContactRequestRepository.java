package com.loci.loci_backend.core.social.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRequestRepository {
  public boolean existsPendingRequest(User a, User b);

  public Optional<ContactRequest> getPendingRequest(UserDBId a, UserDBId b);


  public Optional<ContactRequest> getByPublicId(PublicId id);

  public ContactRequest save(ContactRequest contactRequest);

  public Page<ContactRequest> getAllPendingByReceiver(UserDBId dbId, Pageable pageable);


}
