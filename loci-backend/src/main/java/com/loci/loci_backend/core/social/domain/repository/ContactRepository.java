package com.loci.loci_backend.core.social.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.aggregate.Friend;
import com.loci.loci_backend.core.discovery.domain.vo.SearchQuery;
import com.loci.loci_backend.core.social.domain.aggregate.ContactConnection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactRepository {
  public Optional<ContactConnection> searchContact(UserDBId a, UserDBId b);

  public Optional<ContactConnection> searchContact(User a, User b);

  public boolean existsContactConnection(User a, User b);

  public ContactConnection save(ContactConnection contact);

  public void removeContact(UserDBId a, UserDBId b);

  public void delete(ContactConnection contact);

  public Page<Friend> findConnectedToUser(SearchQuery query, UserDBId userId, Pageable pageable);

  public Page<ContactConnection> findBlockedUsersByUserId(UserDBId dbId, Pageable pageable);
}
