package com.loci.loci_backend.common.user.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.infrastructure.secondary.repository.RestIdentityProvider;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository repository;
  private final RestIdentityProvider restIdentityRepository;

  /**
   * Get current authenticated user
   */
  @Transactional(readOnly = true)
  public User getAuthenticatedUser() {
    return getOptionalUser().orElseThrow(() -> new EntityNotFoundException("Unknow authenticated user"));
  }

  @Transactional(readOnly = true)
  public Optional<User> getOptionalUser() {
    return restIdentityRepository.optionalUsername().flatMap(repository::getByUsername);
  }

}
