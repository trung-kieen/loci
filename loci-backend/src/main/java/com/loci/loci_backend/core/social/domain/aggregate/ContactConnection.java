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
