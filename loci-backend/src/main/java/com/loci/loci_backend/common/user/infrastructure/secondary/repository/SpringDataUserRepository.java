package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Principal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.identity.infrastructure.secondary.specification.UserSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SpringDataUserRepository implements UserRepository {
  private final JpaUserRepository repository;
  private final UserEntityMapper userEntityMapper;

  @Override
  public boolean existByEmail(UserEmail email) {
    return repository.existsByEmail(email.value());
  }

  @Override
  public Optional<User> getByUsername(Username username) {
    return repository.findByUsername(username.username()).map(userEntityMapper::toDomain);
  }

  @Override
  @Transactional(readOnly = false)
  public User createOrUpdate(User user) {
    UserEntity userEntity = userEntityMapper.from(user);
    User savedUser = userEntityMapper.toDomain(repository.saveAndFlush(userEntity));
    return savedUser;
  }

  @Override
  public Optional<User> getByPublicId(PublicId publicId) {
    return repository.findByPublicId(publicId.value()).map(userEntityMapper::toDomain);
  }

  @Override
  public Page<User> searchUser(UserSearchCriteria criteria, Pageable pageable) {
    Page<UserEntity> entityPage = repository.findAll(UserSpecifications.fromCriteria(criteria), pageable);
    return entityPage.map(userEntityMapper::toDomain);
  }

  // @Override
  // public Page<User> getUsersFromIds(List<UserDBId> ids, Pageable pageable) {
  // List<Long> userIds = ids.stream().map(UserDBId::value).toList();
  // Page<UserEntity> entities = repository.findAllById(userIds, pageable);
  //
  // return userEntityMapper.toDomain(entities);
  // }

  @Override
  public List<User> getUsersFromIds(List<UserDBId> ids) {
    List<Long> userIds = ids.stream().map(UserDBId::value).toList();
    List<UserEntity> entities = repository.findAllById(userIds);
    return userEntityMapper.toDomain(entities);
  }

  @Override
  public Page<User> getUsersFromIds(List<UserDBId> ids, Pageable pageable) {
    List<Long> userIds = ids.stream().map(UserDBId::value).toList();
    Page<UserEntity> entities = repository.findByIdIn(userIds, pageable);
    return userEntityMapper.toDomain(entities);
  }

  @Override
  public User findByPrincipal(Principal principal) {
    return get(principal).orElseThrow(() -> new ResourceNotFoundException(principal.getUsername()));
  }

  @Override
  public Optional<User> get(Principal principal) {
    return getByUsername(principal.getUsername());
  }

}
