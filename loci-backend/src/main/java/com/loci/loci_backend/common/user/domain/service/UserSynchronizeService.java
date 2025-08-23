package com.loci.loci_backend.common.user.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSynchronizeService {

  private final UserRepository userRepository;

  @Transactional(readOnly = false)
  public void syncUser(User user) {
    if (user == null) {
      throw new EntityNotFoundException("Error while user sync");
    }

    User finalUser = user;
    Optional<User> dbUser = userRepository.getByUsername(user.getUsername());

    if (dbUser.isPresent()) {
      finalUser = dbUser.get();
      finalUser.updateFromUser(user);
    }
    userRepository.save(finalUser);
  }

}
