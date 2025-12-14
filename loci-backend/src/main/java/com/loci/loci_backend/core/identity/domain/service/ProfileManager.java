package com.loci.loci_backend.core.identity.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.repository.UserConnectionResolver;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProfileManager {
  private final ProfileRepository repository;
  private final UserRepository userRepository;
  private final ProfileAggregateMapper profileMapper;
  private final KeycloakPrincipal principal;
  private final UserConnectionResolver connectionResolver;

  @Transactional(readOnly = true)
  public PersonalProfile readPersonalProfile(KeycloakPrincipal keycloakPrincipal) {

    PersonalProfile profile = repository.findPersonalProfile(keycloakPrincipal.getUserEmail());
    if (profile.existManadatoryField()) {
      return profile;
    }
    profile.initMandatoryField();
    PersonalProfileChanges changes = profileMapper.extractChanges(profile);
    return repository.applyProfileUpdate(profile.getUsername(), changes);
  }

  private PublicProfile queryProfileFromUser(ProfilePublicId profilePublicId, Username username) {
    if (PublicId.isValid(profilePublicId.value())) {

      PublicId userId = ProfilePublicId.toPublicId(profilePublicId);
      return repository.findPublicProfileByUserIdOrUserName(userId, username);

    }
    return repository.findPublicProfileUserName(username);
  }

  @Transactional(readOnly = true)
  public PublicProfile readPublicProfileByPublicId(ProfilePublicId profilePublicId) {
    Optional<User> currentUser = userRepository.getByUsername(principal.getUsername());

    UserDBId currentUserId = null;
    if (currentUser.isPresent()) {
      currentUserId = currentUser.get().getDbId();
    }

    log.debug("Current request principal", principal);
    Username username = ProfilePublicId.toUserName(profilePublicId);
    PublicProfile profile = queryProfileFromUser(profilePublicId, username);
    FriendshipStatus connectionStatus = connectionResolver.aggreateConnection(currentUserId, profile.getUserDbId());
    profile.setConnectionStatus(connectionStatus);
    return profile;
  }

  public PersonalProfile applyUpdate(KeycloakPrincipal keycloakPrincipal, PersonalProfileChanges profileChanges) {
    return repository.applyProfileUpdate(keycloakPrincipal.getUsername(), profileChanges);
  }

  public UserSettings readProfileSettings(KeycloakPrincipal keycloakPrincipal) {
    User currentUser = userRepository.getByUsername(principal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());
    UserSettings settings = repository.readProfileSettings(currentUser.getDbId());

    return settings;
  }

  public UserSettings applyUpdate(KeycloakPrincipal keycloakPrincipal, ProfileSettingChanges settingsChanges) {
    UserSettings settings = this.readProfileSettings(keycloakPrincipal);

    profileMapper.applyChanges(settings, settingsChanges);
    return repository.save(settings);
  }

}
