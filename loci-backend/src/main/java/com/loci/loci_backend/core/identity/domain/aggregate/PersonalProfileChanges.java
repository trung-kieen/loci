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

package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonalProfileChanges {

  private UserFullname fullname;

  // Not apply change this
  // private Username username;

  // Not apply change this
  // private UserEmail email;

  private ProfileBio bio;

  private UserImageUrl imageUrl;

  // private PrivacySetting privacySetting;

  @Builder(style = BuilderStyle.STAGED)
  public PersonalProfileChanges(UserFullname fullname, ProfileBio bio, UserImageUrl imageUrl) {
    this.fullname = fullname;
    this.bio = bio;
    this.imageUrl = imageUrl;
  }

}
