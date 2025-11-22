package com.loci.loci_backend.core.user.infrastructure.primary.resource;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.user.application.ProfileApplicationService;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfilePublicId;
import com.loci.loci_backend.core.user.infrastructure.primary.RestPersonalProfile;
import com.loci.loci_backend.core.user.infrastructure.primary.RestPublicProfile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
public class ProfileResource {
  private final ProfileApplicationService profileService;

  @GetMapping("me")
  public ResponseEntity<RestPersonalProfile> currentUserProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = profileService.getPersonalProfile(keycloakPrincipal);
    return ResponseEntity.ok(RestPersonalProfile.from(profile));
  }

  @GetMapping("{publicId}")
  public ResponseEntity<RestPublicProfile> getPublicProfile(KeycloakPrincipal keycloakPrincipal,
      @PathVariable("publicId") String publicId) {
    ProfilePublicId profilePublicId = ProfilePublicId.from(publicId);

    PublicProfile publicProfile = profileService.getPublicProfile(profilePublicId);

    log.info(publicProfile);
    return ResponseEntity.ok(RestPublicProfile.from(publicProfile));

  }

}
