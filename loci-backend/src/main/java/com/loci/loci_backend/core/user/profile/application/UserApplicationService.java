package com.loci.loci_backend.core.user.profile.application;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.profile.domain.service.ProfileCURD;
import com.loci.loci_backend.core.user.profile.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.user.profile.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
  private final ProfileCURD profileCURD;

  public PersonalProfile getPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = profileCURD.readPersonalProfile(keycloakPrincipal);
    return profile;
  }

  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileCURD.readPublicProfileByPublicId(profilePublicId);
  }

  public PersonalProfile updateProfile(KeycloakPrincipal keycloakPrincipal, PersonalProfileChanges profileChanges) {
    PersonalProfile savedProfile = profileCURD.applyUpdate(keycloakPrincipal, profileChanges);
    return savedProfile;
  }

  public Page<PublicProfile> searchActiveUsers(UserSearchCriteria criteria, Pageable pageable) {
    return profileCURD.searchActiveUsers(criteria, pageable);
  }



}
