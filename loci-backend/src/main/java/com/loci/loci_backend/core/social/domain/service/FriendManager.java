package com.loci.loci_backend.core.social.domain.service;

import java.util.List;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.core.discovery.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.identity.domain.repository.IdentityUserRepository;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestListBuilder;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;
import com.loci.loci_backend.core.social.domain.repository.ContactRequestRepository;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.FriendRequestStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendManager {
  private final UserRepository userRepository;
  private final IdentityUserRepository identityUserRepository;
  private final ContactRepository contactRepository;
  private final ContactRequestRepository contactRequestRepository;
  private final KeycloakPrincipal keycloakPrincipal;

  public FriendshipStatus blockUser(PublicId userId) {
    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    Contact contact = contactRepository.searchContact(toBlockUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(currentUser.getDbId());

    // TODO: websocket - send ack for block conversation/contact

    // TODO: notification - user block them, update converstaion state
    Contact savedContact = contactRepository.save(contact);

    FriendshipStatus updatedStatus = savedContact.friendshipStatusWithUser(currentUser.getDbId());
    return updatedStatus;
  }

  public FriendshipStatus unblockUser(PublicId userId) {
    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    Contact contact = contactRepository.searchContact(toBlockUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(null);

    Contact savedContact = contactRepository.save(contact);
    FriendshipStatus updatedStatus = savedContact.friendshipStatusWithUser(currentUser.getDbId());

    // TODO: websocket - send ack for block conversation/contact

    // TODO: notification - user block them, update converstaion state
    return updatedStatus;

  }

  @Transactional(readOnly = false)
  public ContactRequest sendRequest(CreateContactRequest request) {
    User sender = userRepository.getByUsername(request.getSenderUsername())
        .orElseThrow(() -> new EntityNotFoundException("Not found sender information"));
    User receiver = userRepository.getByPublicId(request.getReceiverPublicId())
        .orElseThrow(() -> new EntityNotFoundException("Not found target contact information"));

    // Not allow to request to friend
    if (contactRepository.existsContactConnection(sender, receiver)) {
      throw new DuplicateResourceException("Already friend", "friend conntection");
    }

    if (sender.equals(receiver)) {
      // TODO: create Bad_request type for this error
      throw new IllegalArgumentException("Unable to send request to yourself");
    }

    // Not allow to duplicate the request
    if (contactRequestRepository.existsPendingRequest(sender, receiver)) {
      throw new DuplicateResourceException("Request already sent", "friend request");
    }

    ContactRequest contactRequest = ContactRequest.builderRequest(sender, receiver);
    contactRequest.assertManadatoryField();
    contactRequest.initManadatoryField();

    ContactRequest savedRequest = contactRequestRepository.save(contactRequest);
    return savedRequest;

  }

  private Contact acceptRequest(ContactRequest request, UserDBId currentUserId, UserDBId friendUserId) {

    request.setStatus(FriendRequestStatus.ACCEPTED);

    contactRequestRepository.save(request);
    // Create contact connection

    Contact contact = Contact.createConnection(currentUserId, friendUserId);
    Contact savedContacted = contactRepository.save(contact);
    // TODO: Send notification accept request

    return savedContacted;
  }

  @Transactional(readOnly = false)
  public Contact acceptRequestForUser(PublicId friendId) {

    User friendUser = userRepository.getByPublicId(friendId)
        .orElseThrow(() -> new EntityNotFoundException("Not found request user information"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    ContactRequest request = contactRequestRepository.getPendingRequest(friendUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    return acceptRequest(request, currentUser.getDbId(), friendUser.getDbId());
  }

  @Transactional(readOnly = false)
  public Contact acceptRequestForRequest(PublicId requestId) {

    ContactRequest request = contactRequestRepository.getByPublicId(requestId)
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));

    return acceptRequest(request, request.getRequestUserId(), request.getReceiverUserId());
  }

  public void rejectRequest(PublicId friendId) {
    User friendUser = userRepository.getByPublicId(friendId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user send this request"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    ContactRequest request = contactRequestRepository.getPendingRequest(friendUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));

    request.setStatus(FriendRequestStatus.DECLINED);

    contactRequestRepository.save(request);
  }

  @Transactional(readOnly = false)
  public void unfriend(PublicId friendId) {
    User friendUser = userRepository.getByPublicId(friendId)
        .orElseThrow(() -> new EntityNotFoundException("Not found friend information"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    contactRepository.removeContact(currentUser.getDbId(), friendUser.getDbId());
  }

  public ContactRequestList viewListContactRequest(Pageable pageable) {
    User user = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());
    ;
    Page<ContactRequest> requests = contactRequestRepository.getAllPendingByReceiver(user.getDbId(), pageable);
    List<UserDBId> userIds = requests.getContent().stream().map(ContactRequest::getRequestUserId).toList();
    var userInfomations = identityUserRepository.getUserSummary(userIds);

    return ContactRequestListBuilder.contactRequestList()
        .contacts(requests)
        .userSummaries(userInfomations)
        .build();
  }

}
