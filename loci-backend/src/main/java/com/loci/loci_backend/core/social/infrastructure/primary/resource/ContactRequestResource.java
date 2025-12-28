package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.primary.mapper.RestContactMapper;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestList;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestFriendshipUpdatedResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contact-requests")
@RequiredArgsConstructor
class ContactRequestResource {

  private final SocialApplicationService socialApplicationService;
  private final RestContactMapper contactMapper;

  @PostMapping("/{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  public RestFriendshipUpdatedResponse sendRequest(@PathVariable("userId") UUID receiverPublicId,
      KeycloakPrincipal sender) {

    CreateContactRequest contactRequest = contactMapper.toDomain(receiverPublicId, sender);
    ContactRequest savedRequest = socialApplicationService.addContactRequest(contactRequest);

    return new RestFriendshipUpdatedResponse(FriendshipStatus.pendingFriendRequest());
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public RestFriendshipUpdatedResponse unsendRequest(@PathVariable("userId") UUID receiverPublicId,
      KeycloakPrincipal sender) {

    PublicId requestUserPubicId = new PublicId(receiverPublicId);
    socialApplicationService.unsendRequestToUser(requestUserPubicId);

    return new RestFriendshipUpdatedResponse(FriendshipStatus.notConnected());
  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public RestContactRequestList getAllRequest(Pageable pageable) {

    ContactRequestList requests = socialApplicationService.viewListContactRequest(pageable);
    return contactMapper.from(requests);
  }

  @PostMapping("/user/{requestUserId}/accept")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse acceptContactRequestFromUser(@PathVariable("requestUserId") UUID requestUserId) {
    PublicId requestUserPubicId = new PublicId(requestUserId);
    socialApplicationService.acceptContactRequestFromUser(requestUserPubicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.connected());
  }

  @PostMapping("/{requestId}/accept")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse acceptContactForRequest(@PathVariable("requestId") UUID requestId) {
    PublicId requestPublicId = new PublicId(requestId);
    socialApplicationService.acceptContactRequestForRequest(requestPublicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.connected());
  }

  @PostMapping("/{requestId}/reject")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse rejectContactRequestForRequest(@PathVariable("requestId") UUID requestId) {
    PublicId requestPubicId = new PublicId(requestId);
    socialApplicationService.rejectContactRequestForRequest(requestPubicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.notConnected());

  }

  @PostMapping("/user/{requestUserId}/reject")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse rejectContactRequestFromUser(@PathVariable("requestUserId") UUID requestUserId) {
    PublicId requestUserPubicId = new PublicId(requestUserId);
    socialApplicationService.rejectContactRequestFromUser(requestUserPubicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.notConnected());

  }

}
