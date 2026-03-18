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

package com.loci.loci_backend.common.user.domain.aggregate;

import java.time.Instant;
import java.util.Set;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

  private PublicId userPublicId;

  private UserDBId dbId;

  private UserEmail email;

  private UserFirstname firstname;

  private UserLastname lastname;

  private Username username;

  private UserImageUrl profilePicture;

  private ProfileBio bio;

  private Instant createdDate;

  private Instant lastModifiedDate;

  private Instant lastActive;

  // private UserSettings privacySetting;

  private Set<Authority> authorities;

  @Builder(style = BuilderStyle.STAGED)
  public User(PublicId userPublicId,
      UserDBId dbId,
      UserEmail email,
      UserFirstname firstname,
      UserLastname lastname,
      Username username,
      UserImageUrl profilePicture,
      Instant createdDate,
      Instant lastModifiedDate,
      ProfileBio bio,
      Instant lastActive,
      // UserSettings privacySetting,
      Set<Authority> authorities) {
    this.userPublicId = userPublicId;
    this.dbId = dbId;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.profilePicture = profilePicture;
    this.createdDate = createdDate;
    this.lastModifiedDate = lastModifiedDate;
    this.bio = bio;
    this.lastActive = lastActive;
    // this.privacySetting = privacySetting;
    this.authorities = authorities;
  }

  public void assertMandatoryFields() {
    Assert.notNull("email", email);
    Assert.notNull("lastname", lastname);
    Assert.notNull("firstname", firstname);
    Assert.notNull("username", username);
    Assert.notNull("authorities", authorities);
  }

  /**
   * Update new data of user in keycloak to database user
   */
  public void syncOauth2User(User user) {

    if (user != null) {
      if (user.userPublicId != null) {
        this.userPublicId = user.userPublicId;
      }
      if (user.email != null) {
        this.email = user.email;
      }
      if (user.firstname != null) {
        this.firstname = user.firstname;
      }
      if (user.lastname != null) {
        this.lastname = user.lastname;
      }
      if (user.username != null) {
        this.username = user.username;
      }
      if (user.profilePicture != null) {
        this.profilePicture = user.profilePicture;
      }
      if (user.authorities != null) {
        this.authorities = user.authorities;
      }
    }
  }

  public void provideMandatoryField() {
    initFieldForSignup();
  }

  public void initFieldForSignup() {
    if (this.userPublicId == null || userPublicId.value() == null) {
      this.userPublicId = PublicId.random();
    }
    if (this.profilePicture == null || profilePicture.value() == null) {
      this.profilePicture = UserImageUrl.random();
    }
    // TODO: init with repository
    // if (this.privacySetting == null) {
    // this.privacySetting =
    // .lastSeenSetting(UserLastSeenSetting.ofDefault())
    // .friendRequestSetting(UserFriendRequestSetting.ofDefault())
    // .profileVisibility(ProfileVisibility.ofDefault())
    // .build();
    // }
  }

  public Username getUsername() {
    return username;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((userPublicId == null) ? 0 : userPublicId.hashCode());
    result = prime * result + ((dbId == null) ? 0 : dbId.hashCode());
    return result;
  }

  public UserFullname getFullname() {
    return UserFullname.from(this.firstname, this.lastname);
  }
  public boolean isOwner(Message message){
    return dbId.equals(message.getSenderId());

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (userPublicId == null) {
      if (other.userPublicId != null) {
        return false;
      }
    } else if (!userPublicId.equals(other.userPublicId)) {
      return false;
    }
    if (dbId == null) {
      if (other.dbId != null) {
        return false;
      }
    } else if (!dbId.equals(other.dbId)) {
      return false;
    }
    return true;
  }
}
