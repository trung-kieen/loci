package com.loci.loci_backend.core.user.domain.profile.service;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.repository.ProfileRepository;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfilePublicId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileReader {
  private final ProfileRepository repository;

  public PersonalProfile readPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    return repository.findPersonalProfile(keycloakPrincipal.getUserEmail());
  }

  public PublicProfile readPublicProfileByPublicId(ProfilePublicId profilePublicId) {
    Username username = ProfilePublicId.toUserName(profilePublicId);
    if (UserPublicId.isValid(profilePublicId.value())) {

      UserPublicId userId = ProfilePublicId.toUserPublicId(profilePublicId);
      return repository.findPublicProfileByUserIdOrUserName(userId, username);

    }
    return repository.findPublicProfileUserName(username);
  }

}
