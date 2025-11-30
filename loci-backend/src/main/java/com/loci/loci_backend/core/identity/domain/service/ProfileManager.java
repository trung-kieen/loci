package com.loci.loci_backend.core.identity.domain.service;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.repository.ProfileRepository;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.identity.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileManager {
  private final ProfileRepository repository;
  private final UserAggregateMapper profileMapper;

  public PersonalProfile readPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = repository.findPersonalProfile(keycloakPrincipal.getUserEmail());
    if (profile.existManadatoryField()) {
      return profile;
    }
    profile.initMandatoryField();
    PersonalProfileChanges changes = profileMapper.extractChanges(profile);
    return repository.applyProfileUpdate(profile.getUsername(), changes);
  }

  public PublicProfile readPublicProfileByPublicId(ProfilePublicId profilePublicId) {
    Username username = ProfilePublicId.toUserName(profilePublicId);
    if (UserPublicId.isValid(profilePublicId.value())) {

      UserPublicId userId = ProfilePublicId.toUserPublicId(profilePublicId);
      return repository.findPublicProfileByUserIdOrUserName(userId, username);

    }
    return repository.findPublicProfileUserName(username);
  }

  public PersonalProfile applyUpdate(KeycloakPrincipal keycloakPrincipal, PersonalProfileChanges profileChanges) {
    return repository.applyProfileUpdate(keycloakPrincipal.getUsername(), profileChanges);
  }

  public Page<PublicProfile> searchActiveUsers(UserSearchCriteria criteria, Pageable pageable) {
    return repository.searchActiveUsers(criteria, pageable);
  }

}
