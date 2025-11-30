package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.domain.service.UserAggregateMapper;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataProfileRepository implements ProfileRepository {
  private final JpaUserRepository userRepository;
  private final UserAggregateMapper userMapper;
  private final UserEntityMapper userEntityMapper;

  private Optional<UserEntity> findByUsernameOpt(Username username) {
    return userRepository.findByEmail(username.username());
  }

  @Override
  public PersonalProfile findPersonalProfile(UserEmail userEmail) {
    UserEntity userEntity = userRepository.findByEmail(userEmail.value())
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    return userEntityMapper.toPersonalProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileByUserIdOrUserName(UserPublicId userId, Username username) {
    // Find by public id first else fall back to username
    UserEntity userEntity = userRepository.findByPublicId(userId.value()).orElseGet(() -> {
      return findByUsernameOpt(username)
          .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    });

    return userEntityMapper.toPublicProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileUserName(Username username) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));

    return userEntityMapper.toPublicProfile(userEntity);
  }

  @Override
  public PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    userEntity.applyChanges(profileChanges);
    UserEntity savedEntity = userRepository.save(userEntity);
    return userEntityMapper.toPersonalProfile(savedEntity);
  }

}
