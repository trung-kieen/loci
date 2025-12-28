package com.loci.loci_backend.core.identity.domain.service;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.identity.domain.repository.IdentityUserRepository;
import com.loci.loci_backend.core.social.domain.aggregate.Contact;
import com.loci.loci_backend.core.social.domain.repository.ContactRepository;
import com.loci.loci_backend.core.social.domain.repository.ContactRequestRepository;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlockManager {

  private final UserRepository userRepository;
  private final IdentityUserRepository identityUserRepository;
  private final ContactRepository contactRepository;
  private final ContactRequestRepository contactRequestRepository;
  private final KeycloakPrincipal keycloakPrincipal;

  @Transactional(readOnly = false)
  public FriendshipStatus blockUser(PublicId userId) {
    // TODO: If exist blocked not allow duplicate in other side

    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    Contact contact = null;
    Optional<Contact> contactOpt = contactRepository.searchContact(toBlockUser.getDbId(), currentUser.getDbId());
    if (contactOpt.isPresent()) {
      contact = contactOpt.get();
    } else {
      contact = Contact.createConnection(currentUser.getDbId(), toBlockUser.getDbId());
    }

    // .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(currentUser.getDbId());
    //
    // // TODO: websocket - send ack for block conversation/contact
    //
    // // TODO: notification - user block them, update converstaion state
    Contact savedContact = contactRepository.save(contact);

    FriendshipStatus updatedStatus = savedContact.friendshipStatusWithUser(currentUser.getDbId());
    return updatedStatus;
  }

  @Transactional(readOnly = false)
  public FriendshipStatus unblockUser(PublicId userId) {

    User toBlockUser = userRepository.getByPublicId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Not found user information to block"));
    User currentUser = userRepository.getByUsername(keycloakPrincipal.getUsername())
        .orElseThrow(() -> new EntityNotFoundException());

    // Search for contact of user
    Contact contact = contactRepository.searchContact(toBlockUser.getDbId(), currentUser.getDbId())
        .orElseThrow(() -> new EntityNotFoundException("Not found contact request"));
    contact.setBlockedByUserId(null);

    FriendshipStatus newStatus = null;
    // TODO: policy
    // if (contactRequestRepository.existsAcceptedRequest(currentUser.getDbId(), toBlockUser.getDbId())) {
    //
    //   // Check if previous is exist connected
    //   newStatus = FriendshipStatus.CONNECTED;
    //   contact.setBlockedByUserId(null);
    //   contactRepository.save(contact);
    // } else {
    //
      newStatus = FriendshipStatus.connected();
      contactRepository.delete(contact);
    //
    // }

    // TODO: websocket - send ack for block conversation/contact

    // TODO: notification - user block them, update converstaion state

    return newStatus;

  }

}
