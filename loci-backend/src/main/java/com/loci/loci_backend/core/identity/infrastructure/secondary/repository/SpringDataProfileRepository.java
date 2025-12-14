package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataProfileRepository implements ProfileRepository {
  private final JpaUserRepository userRepository;
  private final JpaUserSettingRepository userSettingRepository;
  private final IdentityEntityMapper profileMapper;

  private Optional<UserEntity> findByUsernameOpt(Username username) {
    return userRepository.findByUsername(username.username());
  }

  @Override
  public PersonalProfile findPersonalProfile(UserEmail userEmail) {
    UserEntity userEntity = userRepository.findByEmail(userEmail.value())
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    return profileMapper.toPersonalProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileByUserIdOrUserName(PublicId userId, Username username) {
    // Find by public id first else fall back to username
    UserEntity userEntity = userRepository.findByPublicId(userId.value()).orElseGet(() -> {
      return findByUsernameOpt(username)
          .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    });

    return profileMapper.toPublicProfile(userEntity);
  }

  @Override
  public PublicProfile findPublicProfileUserName(Username username) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));

    return profileMapper.toPublicProfile(userEntity);
  }

  @Override
  public PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges) {
    UserEntity userEntity = findByUsernameOpt(username)
        .orElseThrow(() -> new EntityNotFoundException("Personal profile information not found"));
    userEntity.applyChanges(profileChanges);

    UserEntity savedEntity = userRepository.save(userEntity);
    return profileMapper.toPersonalProfile(savedEntity);
  }

  @Override
  public UserSettings readProfileSettings(UserDBId dbId) {
    Long userDbId = dbId.value();
    UserSettingsEntity settings = userSettingRepository.findById(userDbId)
        .orElseThrow(() -> new EntityNotFoundException("Settings is not exists for user"));
    return profileMapper.toDomain(settings);
  }

  @Override
  public UserSettings save(UserSettings settings) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }


}
