package com.loci.loci_backend.common.user.domain.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

  Optional<User> getByPublicId(PublicId publicId);

  Optional<User> getByUsername(Username username);

  boolean existByEmail(UserEmail email);

  User createOrUpdate(User user);

  // Move to discovery boundcontext
  Page<User> searchUser(UserSearchCriteria criteria, Pageable pageable);

  // public Page<User> getUsersFromIds(List<UserDBId> ids, Pageable pageable);

  public List<User> getUsersFromIds(List<UserDBId> ids);

  public Page<User> getUsersFromIds(List<UserDBId> suggestUserIds, Pageable pageable);

  public User findByPrincipal(CurrentUser principal);

  public Optional<User> get(CurrentUser principal);




}
