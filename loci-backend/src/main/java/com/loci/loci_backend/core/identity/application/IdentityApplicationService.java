package com.loci.loci_backend.core.identity.application;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.core.discovery.application.DiscoveryApplicationService;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContact;
import com.loci.loci_backend.core.discovery.domain.vo.ContactSearchCriteria;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.service.ProfileManager;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityApplicationService {
  private final ProfileManager profileManager;
  private final DiscoveryApplicationService discoveryApplicationService;

  public PersonalProfile getPersonalProfile(KeycloakPrincipal keycloakPrincipal) {
    PersonalProfile profile = profileManager.readPersonalProfile(keycloakPrincipal);
    return profile;
  }

  public Page<SearchContact> discoveryContacts(ContactSearchCriteria criteria, Pageable pageable,
      KeycloakPrincipal principal) {
    return discoveryApplicationService.discoveryContacts(criteria, pageable, principal);
  }

  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileManager.readPublicProfileByPublicId(profilePublicId);
  }

  public PersonalProfile updateProfile(KeycloakPrincipal keycloakPrincipal, PersonalProfileChanges profileChanges) {
    PersonalProfile savedProfile = profileManager.applyUpdate(keycloakPrincipal, profileChanges);
    return savedProfile;
  }

  public UserSettings getPersonalProfileSettings(KeycloakPrincipal keycloakPrincipal) {
    UserSettings settings = profileManager.readProfileSettings(keycloakPrincipal);
    return settings;
  }

  public UserSettings updateProfileSettings(KeycloakPrincipal keycloakPrincipal,
      ProfileSettingChanges settingsChanges) {
    return profileManager.applyUpdate(keycloakPrincipal, settingsChanges);
  }

}
