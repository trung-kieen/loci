package com.loci.loci_backend.core.user.application;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.service.ProfileReader;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfilePublicId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileApplicationService {
  private final ProfileReader profileReader;

  public PersonalProfile getPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    return profileReader.readPersonalProfile(keycloakPrincipal);
  }


  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileReader.readPublicProfileByPublicId(profilePublicId);
  }



}
