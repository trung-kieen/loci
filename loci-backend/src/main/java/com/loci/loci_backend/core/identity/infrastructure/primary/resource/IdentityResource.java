package com.loci.loci_backend.core.identity.infrastructure.primary.resource;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.discovery.infrastructure.primary.mapper.RestContactMapper;
import com.loci.loci_backend.core.discovery.infrastructure.primary.payload.RestContact;
import com.loci.loci_backend.core.identity.application.IdentityApplicationService;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;
import com.loci.loci_backend.core.identity.infrastructure.primary.mapper.RestProfileMapper;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class IdentityResource {
  private final IdentityApplicationService identityApplicationService;
  private final RestContactMapper contactRestMapper;
  private final RestProfileMapper restProfileMapper;

  @GetMapping("search")
  public ResponseEntity<Page<RestContact>> searchUser(
      @RequestParam(required = false, defaultValue = "", value = "q") String query,
      Pageable pageable) {

    ContactSearchCriteria criteria = new ContactSearchCriteria(query);
    return ResponseEntity.ok(contactRestMapper.from(identityApplicationService.discoveryContacts(criteria, pageable)));
  }

  @GetMapping("me")
  public ResponseEntity<RestPersonalProfile> currentUserProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = identityApplicationService.getPersonalProfile(keycloakPrincipal);
    log.debug(profile);
    return ResponseEntity.ok(restProfileMapper.from(profile));
  }

  @PatchMapping("me")
  public ResponseEntity<RestPersonalProfile> partialUpdateProfile(KeycloakPrincipal keycloakPrincipal,
      @RequestBody RestPersonalProfilePatch patchRequest) {
    PersonalProfileChanges profileChages = restProfileMapper.toDomain(patchRequest);
    PersonalProfile updatedProfile = identityApplicationService.updateProfile(keycloakPrincipal, profileChages);

    return ResponseEntity.ok(restProfileMapper.from(updatedProfile));
  }

  @PatchMapping("me/avatar")
  public ResponseEntity<RestPersonalProfile> updateProfileImage(KeycloakPrincipal keycloakPrincipal,
      @RequestParam("image") MultipartFile file) {

    // validate file

    // upload image to file storage

    // Use image link to patch update profile
    // PersonalProfileChanges profileChages = restProfileMapper.toDomain(patchRequest);
    // PersonalProfile updatedProfile = identityApplicationService.updateProfile(keycloakPrincipal, profileChages);
    //
    // return ResponseEntity.ok(restProfileMapper.from(updatedProfile));
    throw new NotImplementedException();
  }

  @GetMapping("{publicId}")
  public ResponseEntity<RestPublicProfile> getPublicProfile(KeycloakPrincipal keycloakPrincipal,
      @PathVariable("publicId") String publicId) {
    ProfilePublicId profilePublicId = ProfilePublicId.from(publicId);

    PublicProfile publicProfile = identityApplicationService.getPublicProfile(profilePublicId);

    log.debug(publicProfile);
    return ResponseEntity.ok(restProfileMapper.from(publicProfile));

  }

}
