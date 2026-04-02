/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.identity.application;

import java.util.Map;
import java.util.Set;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.discovery.application.DiscoveryApplicationService;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactProfileList;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupPresence;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;
import com.loci.loci_backend.core.identity.domain.service.PresenceIndicator;
import com.loci.loci_backend.core.identity.domain.service.ProfileManagerService;
import com.loci.loci_backend.core.identity.domain.service.UserPresenceService;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
import com.loci.loci_backend.core.identity.domain.vo.ProfilePublicId;

import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class IdentityApplicationService {
  private final ProfileManagerService profileManager;
  private final DiscoveryApplicationService discoveryApplicationService;
  private final PresenceIndicator presenceIndicator;
  private final UserPresenceService userPresenceService;

  public PersonalProfile getPersonalProfile() {
    PersonalProfile profile = profileManager.readPersonalProfile();
    return profile;
  }

  public ContactProfileList discoveryContacts(UserSearchCriteria criteria, Pageable pageable) {
    return discoveryApplicationService.discoveryContacts(criteria, pageable);
  }

  public PublicProfile getPublicProfile(ProfilePublicId profilePublicId) {
    return profileManager.readPublicProfileByPublicId(profilePublicId);
  }

  public PersonalProfile updateProfile(PersonalProfileChanges profileChanges) {
    PersonalProfile savedProfile = profileManager.applyUpdate(profileChanges);
    return savedProfile;
  }

  public UserSetting getPersonalProfileSettings() {
    UserSetting settings = profileManager.readProfileSettings();
    return settings;
  }

  public UserSetting updateProfileSettings(ProfileSettingChanges settingsChanges) {
    return profileManager.applyUpdate(settingsChanges);
  }

  public PersonalProfile updateProfileAvatar(File newProfileImage) {
    return profileManager.applyUpdate(newProfileImage);

  }

  public ContactProfileList suggestFriends(SuggestFriendCriteria criteria, Pageable pageable) {
    return discoveryApplicationService.suggestFriends(criteria, pageable);
  }

  public void setOnline(PresenceId presenceId, PresenceStatus status) {
    this.presenceIndicator.setOnline(presenceId, status);
  }

  public UserPresence setOfflineCurrentUser() {
    return this.userPresenceService.setOfflineCurrentUser();
  }

  public void handleImplicitOffline(PresenceId presenceId) {
    this.presenceIndicator.handleImplicitOffline(presenceId);
  }

  public UserPresence heartbeatCurrentUser() {
    return this.userPresenceService.heartbeatCurrentUser();
  }

  public UserPresence heartbeat(PresenceId presenceId) {
    return this.userPresenceService.heartbeat(presenceId);
  }

  public UserPresence getStatus(PresenceId presenceId) {
    return this.presenceIndicator.getStatus(presenceId);
  }

  public Map<PresenceId, UserPresence> getMultipleStatuses(Set<PresenceId> presenceIds) {
    return this.presenceIndicator.getMultipleStatuses(presenceIds);
  }

  public GroupPresence getGroupPresence(ConversationId conversationId) {
    return this.presenceIndicator.getGroupPresence(conversationId);
  }

  public UserPresence getCurrentUserStatus() {
    return this.userPresenceService.getCurrentUserStatus();
  }

  // public int sweepStaleUsers(long threshold) {
  // return this.presenceIndicator.sweepStaleUsers(threshold);
  // }

}
