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

package com.loci.loci_backend.core.discovery.domain.aggregate;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContactProfile {

  private UserDBId userDBId;

  private PublicId publicId;

  private UserFullname fullname;

  private Username username;

  private UserEmail email;

  private UserImageUrl imageUrl;

  private FriendshipStatus friendshipStatus;


  @Builder(style = BuilderStyle.STAGED)
  public ContactProfile(UserDBId userDBId, PublicId publicId, UserFullname fullname, Username username, UserEmail userEmail,
      UserImageUrl imageUrl, FriendshipStatus friendshipStatus) {
    this.userDBId = userDBId;
    this.publicId = publicId;
    this.fullname = fullname;
    this.username = username;
    this.email = userEmail;
    this.imageUrl = imageUrl;
    this.friendshipStatus = friendshipStatus;
  }
}
