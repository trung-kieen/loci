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

package com.loci.loci_backend.core.social.infrastructure.primary.payload;

import java.util.UUID;

import com.loci.loci_backend.core.social.infrastructure.secondary.enumernation.FriendshipStatusEnum;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestContactRequest {
  private UUID userId;
  private String fullname;
  private String username;
  private String email;
  private String imageUrl;
  private String friendshipStatus = FriendshipStatusEnum.REQUEST_RECEIVED.value();

  @Builder(style = BuilderStyle.STAGED)
  public RestContactRequest(UUID userId, String fullname, String username, String email, String imageUrl,
      String friendshipStatus) {
    this.userId = userId;
    this.fullname = fullname;
    this.username = username;
    this.email = email;
    this.imageUrl = imageUrl;
    this.friendshipStatus = friendshipStatus;
  }

}
