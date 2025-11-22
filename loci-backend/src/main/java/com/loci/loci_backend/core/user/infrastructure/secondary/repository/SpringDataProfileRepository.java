package com.loci.loci_backend.core.user.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.repository.ProfileRepository;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataProfileRepository implements ProfileRepository {
  private final JpaUserRepository userRepository;

  private Optional<UserEntity> findByUsername(Username username) {
    return userRepository.findByEmail(username.username());
  }

  @Override
  public PersonalProfile findPersonalProfile(UserEmail userEmail) {
    UserEntity userEntity = userRepository.findByEmail(userEmail.value())
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    return UserEntity.toPersonalProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileByUserIdOrUserName(UserPublicId userId, Username username) {
    // Find by public id first else fall back to username
    UserEntity userEntity = userRepository.findByPublicId(userId.value()).orElseGet(() -> {
      return findByUsername(username)
          .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    });

    return UserEntity.toPublicProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileUserName(Username username) {
    UserEntity  userEntity = findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));

    return UserEntity.toPublicProfile(userEntity);
  }

}
