package com.loci.loci_backend.core.social.application;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.identity.domain.service.BlockManager;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.service.FriendManager;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialApplicationService {
  private final FriendManager friendManager;
  private final BlockManager blockManager;

  public ContactRequest addContactRequest(CreateContactRequest contactRequest) {
    return friendManager.sendRequest(contactRequest);

  }

  public Contact acceptContactRequestFromUser(PublicId friendId) {
    return friendManager.acceptRequestFromUser(friendId);
  }

  public Contact acceptContactRequestForRequest(PublicId requestId) {
    return friendManager.acceptRequestForRequest(requestId);
  }

  public ContactRequest sendContactRequest(CreateContactRequest request) {
    return friendManager.sendRequest(request);
  }

  public void removeContactConnection(PublicId friendId) {
    friendManager.unfriend(friendId);
  }

  public void rejectContactRequestFromUser(PublicId requestUserId) {
    this.friendManager.rejectRequestFromUser(requestUserId);
  }

  public void rejectContactRequestForRequest(PublicId requestPubicId) {
    this.friendManager.rejectRequestForRequest(requestPubicId);
  }

  public ContactRequestList viewListContactRequest(Pageable pageable) {
    return friendManager.viewListContactRequest(pageable);
  }

  public FriendshipStatus blockUser(PublicId blockUserId) {
    return blockManager.blockUser(blockUserId);
  }

  public FriendshipStatus unblockUser(PublicId blockUserId) {
    return blockManager.unblockUser(blockUserId);
  }

  public void unsendRequestToUser(PublicId targetUserId) {
    friendManager.unsendRequest(targetUserId);
  }

}
