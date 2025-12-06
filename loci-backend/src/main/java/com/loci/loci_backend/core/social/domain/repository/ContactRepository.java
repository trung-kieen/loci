package com.loci.loci_backend.core.social.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;

public interface ContactRepository {
  public Optional<Contact> searchContact(UserDBId a, UserDBId b);

  public Optional<Contact> searchContact(User a, User b);

  public boolean existsContactConnection(User a, User b);

  public Contact save(Contact contact);


  public void removeContact(UserDBId a, UserDBId b);
}
