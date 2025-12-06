package com.loci.loci_backend.common.user.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<User> getByPublicId(PublicId publicId);


  Optional<User> getByUsername(Username username);

  boolean existByEmail(UserEmail email);

  void save(User user);

  Page<User> searchUser(ContactSearchCriteria criteria, Pageable pageable);


}
