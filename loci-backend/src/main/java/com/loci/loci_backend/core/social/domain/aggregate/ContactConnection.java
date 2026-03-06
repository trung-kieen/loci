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

package com.loci.loci_backend.core.social.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.social.domain.vo.ContactId;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@Data
public class ContactConnection {
  private ContactId contactId;
  private UserDBId owningUserId;
  private UserDBId contactUserId;
  private UserDBId blockedByUserId;

  @Builder(style = BuilderStyle.STAGED)
  public ContactConnection(ContactId contactId, UserDBId owningUserId, UserDBId contactUserId,
      UserDBId blockedByUserId) {
    this.contactId = contactId;
    this.owningUserId = owningUserId;
    this.contactUserId = contactUserId;
    this.blockedByUserId = blockedByUserId;
  }

  public static ContactConnection createConnection(UserDBId a, UserDBId b) {
    return new ContactConnection(null, a, b, null);
  }

  public FriendshipStatus friendshipStatusWithUser(UserDBId currentUserId) {
    if (blockedByUserId == null) {
      return FriendshipStatus.connected();
    }
    // Block by current user
    if (currentUserId == blockedByUserId) {
      return FriendshipStatus.blockedOther();
    }
    // Block by opponent user
    return FriendshipStatus.blockedByOther();
  }

  public UserDBId getOpponentUserId(UserDBId requestUserId) {
    if (contactUserId.equals(requestUserId)) {
      return owningUserId;
    }
    if (owningUserId.equals(requestUserId)) {
      return contactUserId;
    }
    throw new EntityNotFoundException("Not found opponent user");

  }
}
