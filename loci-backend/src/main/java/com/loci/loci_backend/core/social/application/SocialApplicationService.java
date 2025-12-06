package com.loci.loci_backend.core.social.application;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.service.FriendManager;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocialApplicationService {
  private final FriendManager friendManager;

  public ContactRequest addContactRequest(CreateContactRequest contactRequest) {
    return friendManager.sendRequest(contactRequest);

  }

  // TODO: map to api endpoint
  public Contact acceptContactRequestForUser(PublicId friendId) {
    return friendManager.acceptRequestForUser(friendId);
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

  public void rejectContactRequest(PublicId friendId) {
    friendManager.rejectRequest(friendId);
  }

  public ContactRequestList viewListContactRequest(Pageable pageable) {
    return friendManager.viewListContactRequest(pageable);
  }


}
