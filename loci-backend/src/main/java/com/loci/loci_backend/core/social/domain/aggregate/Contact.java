package com.loci.loci_backend.core.social.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.discovery.infrastructure.secondary.dto.UserRelation;
import com.loci.loci_backend.core.social.domain.vo.ContactId;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class Contact {
  private ContactId contactId;
  private UserDBId owningUserId;
  private UserDBId contactUserId;
  private UserDBId blockedByUserId;

  @Builder(style = BuilderStyle.STAGED)
  public Contact(ContactId contactId, UserDBId owningUserId, UserDBId contactUserId, UserDBId blockedByUserId) {
    this.contactId = contactId;
    this.owningUserId = owningUserId;
    this.contactUserId = contactUserId;
    this.blockedByUserId = blockedByUserId;
  }

  public static Contact createConnection(UserDBId a, UserDBId b) {
    return new Contact(null, a, b, null);
  }

  public FriendshipStatus friendshipStatusWithUser(UserDBId currentUserId) {
    if (blockedByUserId == null) {
      return FriendshipStatus.CONNECTED;
    }
    // Block by current user
    if (currentUserId == blockedByUserId) {
      return FriendshipStatus.BLOCKED;
    }
    // Block by opponent user
    return FriendshipStatus.BLOCKED_BY_THEM;
  }
}
