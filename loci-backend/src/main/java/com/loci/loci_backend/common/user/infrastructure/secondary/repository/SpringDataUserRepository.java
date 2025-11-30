package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;

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
    return repository.findByEmail(username.username()).map(userEntityMapper::toDomain);
  }

  @Override
  @Transactional(readOnly = false)
  public void save(User user) {
    var userEntity = userEntityMapper.from(user);
    repository.save(userEntity);
  }

}
