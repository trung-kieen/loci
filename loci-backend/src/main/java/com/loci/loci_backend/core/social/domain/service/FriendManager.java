package com.loci.loci_backend.core.social.domain.service;

import java.util.List;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
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
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FriendManager {
  private final UserRepository userRepository;
  private final IdentityUserRepository identityUserRepository;
  private final ContactRepository contactRepository;
  private final ContactRequestRepository contactRequestRepository;
  private final KeycloakPrincipal keycloakPrincipal;

  @Transactional(readOnly = false)
  public ContactRequest sendRequest(CreateContactRequest request) {
    // TODO: not allow bloceked user to send request
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
    if (!request.getReceiverUserId().equals(currentUserId)) {
      throw new IllegalAccessError("Not permited to accept this request");
    }

    request.setStatus(FriendRequestStatus.ACCEPTED);

    contactRequestRepository.save(request);
    // Create contact connection

    Contact contact = Contact.createConnection(currentUserId, friendUserId);
    Contact savedContacted = contactRepository.save(contact);
    // TODO: Send notification accept request

    return savedContacted;
  }

  @Transactional(readOnly = false)
  public Contact acceptRequestFromUser(PublicId friendId) {

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

  private void rejectRequest(ContactRequest request, UserDBId currentUserId) {
    if (!currentUserId.equals(request.getReceiverUserId())) {
      throw new IllegalAccessError("Not permitted to deny this request");
    }
    request.setStatus(FriendRequestStatus.DECLINED);

    contactRequestRepository.save(request);
  }

  public void rejectRequestFromUser(PublicId friendId) {
    User friendUser = userRepository.getByPublicId(friendId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user send this request"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    ContactRequest request = contactRequestRepository.getPendingRequest(friendUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));

    rejectRequest(request, currentUser.getDbId());
  }

  public void rejectRequestForRequest(PublicId requestId) {
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());
    ContactRequest request = contactRequestRepository.getByPublicId(requestId)
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    rejectRequest(request, currentUser.getDbId());
  }

  @Transactional(readOnly = false)
  public void unfriend(PublicId friendId) {
    User friendUser = userRepository.getByPublicId(friendId)
        .orElseThrow(() -> new EntityNotFoundException("Not found friend information"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    contactRepository.removeContact(currentUser.getDbId(), friendUser.getDbId());

    contactRequestRepository.deleteRequestBetween(currentUser.getDbId(), friendUser.getDbId());
  }

  @Transactional(readOnly = false)
  public void unsendRequest(PublicId targetId) {
    User friendUser = userRepository.getByPublicId(targetId)
        .orElseThrow(() -> new EntityNotFoundException("Not found friend information"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    ContactRequest request = contactRequestRepository.getPendingRequest(currentUser.getDbId(), friendUser.getDbId()).orElseThrow(() -> new EntityNotFoundException("Requset not found"));
    contactRequestRepository.delete(request);
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
