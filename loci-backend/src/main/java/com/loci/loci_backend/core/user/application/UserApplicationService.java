package com.loci.loci_backend.core.user.application;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.MigrationResult;
import com.loci.loci_backend.common.user.domain.service.UserMigrationService;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.service.ProfileCURD;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfilePublicId;
import com.loci.loci_backend.core.user.domain.profile.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
  private final ProfileCURD profileCURD;
  private final UserMigrationService migrationService;

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


  public MigrationResult migrateUsers(int limit) {
    return migrationService.migrateUsers(limit);
  }
  public void clearMigratedUsers() {
    migrationService.clearMigratedUsers();
  }

}
