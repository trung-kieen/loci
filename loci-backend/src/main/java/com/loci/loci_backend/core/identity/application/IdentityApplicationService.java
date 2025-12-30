package com.loci.loci_backend.core.identity.application;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.core.discovery.application.DiscoveryApplicationService;
import com.loci.loci_backend.core.discovery.domain.aggregate.SearchContactList;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.service.ProfileManager;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class IdentityApplicationService {
  private final ProfileManager profileManager;
  private final DiscoveryApplicationService discoveryApplicationService;

  public PersonalProfile getPersonalProfile() {
    PersonalProfile profile = profileManager.readPersonalProfile();
    return profile;
  }

  public SearchContactList discoveryContacts(UserSearchCriteria criteria, Pageable pageable
      ) {
    return discoveryApplicationService.discoveryContacts(criteria, pageable);
  }

  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileManager.readPublicProfileByPublicId(profilePublicId);
  }

  public PersonalProfile updateProfile(PersonalProfileChanges profileChanges) {
    PersonalProfile savedProfile = profileManager.applyUpdate(profileChanges);
    return savedProfile;
  }

  public UserSettings getPersonalProfileSettings() {
    UserSettings settings = profileManager.readProfileSettings();
    return settings;
  }

  public UserSettings updateProfileSettings(ProfileSettingChanges settingsChanges) {
    return profileManager.applyUpdate(settingsChanges);
  }

  public PersonalProfile updateProfileAvatar(File newProfileImage) {
    return profileManager.applyUpdate(newProfileImage);

  }

  public SearchContactList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {
    return discoveryApplicationService.suggestFriends(criteria, pageable);
  }

}
