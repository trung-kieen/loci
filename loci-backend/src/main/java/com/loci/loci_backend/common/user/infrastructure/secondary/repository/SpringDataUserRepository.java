package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.identity.infrastructure.secondary.persistence.UserSpecifications;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  public User  save(User user) {
    UserEntity userEntity = userEntityMapper.from(user);
    return userEntityMapper.toDomain(repository.save(userEntity));
  }

  @Override
  public Optional<User> getByPublicId(PublicId publicId) {
    return repository.findByPublicId(publicId.value()).map(userEntityMapper::toDomain);
  }

  @Override
  public Page<User> searchUser(ContactSearchCriteria criteria, Pageable pageable) {
    String keyword = criteria.getKeyword();
    String currentUsername = criteria.getCurrentUsername();
    Specification<UserEntity> spec = UserSpecifications.searchActiveUsers(keyword, currentUsername);
    Page<UserEntity> entityPage = repository.findAll(spec, pageable);
    return entityPage.map(userEntityMapper::toDomain);
  }


}
