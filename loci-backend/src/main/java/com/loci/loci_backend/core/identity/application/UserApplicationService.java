package com.loci.loci_backend.core.identity.application;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.service.ProfileManager;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.identity.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
  private final ProfileManager profileManager;

  public PersonalProfile getPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = profileManager.readPersonalProfile(keycloakPrincipal);
    return profile;
  }

  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileManager.readPublicProfileByPublicId(profilePublicId);
  }

  public PersonalProfile updateProfile(KeycloakPrincipal keycloakPrincipal, PersonalProfileChanges profileChanges) {
    PersonalProfile savedProfile = profileManager.applyUpdate(keycloakPrincipal, profileChanges);
    return savedProfile;
  }

  public Page<PublicProfile> searchActiveUsers(UserSearchCriteria criteria, Pageable pageable) {
    return profileManager.searchActiveUsers(criteria, pageable);
  }



}
