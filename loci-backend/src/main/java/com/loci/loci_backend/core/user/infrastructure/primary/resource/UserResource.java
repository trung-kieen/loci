package com.loci.loci_backend.core.user.infrastructure.primary.resource;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.user.application.UserApplicationService;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfilePublicId;
import com.loci.loci_backend.core.user.domain.profile.vo.UserSearchCriteria;
import com.loci.loci_backend.core.user.infrastructure.primary.RestPersonalProfile;
import com.loci.loci_backend.core.user.infrastructure.primary.RestPersonalProfilePatch;
import com.loci.loci_backend.core.user.infrastructure.primary.RestPublicProfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserResource {
  private final UserApplicationService userService;

  @GetMapping("search")
  public ResponseEntity<Page<RestPublicProfile>> searchUser(
      @RequestParam(required = false, defaultValue = "", value = "q") String query,
      Pageable pageable) {

    UserSearchCriteria criteria = new UserSearchCriteria(query);
    return ResponseEntity.ok(RestPublicProfile.from(userService.searchActiveUsers(criteria, pageable)));
  }

  @GetMapping("me")
  public ResponseEntity<RestPersonalProfile> currentUserProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = userService.getPersonalProfile(keycloakPrincipal);
    System.out.println(profile);
    return ResponseEntity.ok(RestPersonalProfile.from(profile));
  }

  @PatchMapping("me")
  public ResponseEntity<RestPersonalProfile> partialUpdateProfile(KeycloakPrincipal keycloakPrincipal,
      @RequestBody RestPersonalProfilePatch patchRequest) {
    PersonalProfileChanges profileChages = RestPersonalProfilePatch.toDomain(patchRequest);
    PersonalProfile updatedProfile = userService.updateProfile(keycloakPrincipal, profileChages);

    return ResponseEntity.ok(RestPersonalProfile.from(updatedProfile));
  }

  @GetMapping("{publicId}")
  public ResponseEntity<RestPublicProfile> getPublicProfile(KeycloakPrincipal keycloakPrincipal,
      @PathVariable("publicId") String publicId) {
    ProfilePublicId profilePublicId = ProfilePublicId.from(publicId);

    PublicProfile publicProfile = userService.getPublicProfile(profilePublicId);

    log.info(publicProfile);
    return ResponseEntity.ok(RestPublicProfile.from(publicProfile));

  }

}
