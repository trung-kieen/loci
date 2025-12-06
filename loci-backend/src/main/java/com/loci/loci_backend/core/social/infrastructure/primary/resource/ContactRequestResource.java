package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequest;
import com.loci.loci_backend.core.social.domain.aggregate.ContactRequestList;
import com.loci.loci_backend.core.social.domain.aggregate.CreateContactRequest;
import com.loci.loci_backend.core.social.infrastructure.primary.mapper.RestContactMapper;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestAcceptContactRequest;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestCreated;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestList;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  public RestContactRequestCreated sendRequest(@PathVariable("userId") UUID receiverPublicId,
      KeycloakPrincipal sender) {

    CreateContactRequest contactRequest = contactMapper.toDomain(receiverPublicId, sender);
    ContactRequest savedRequest = socialApplicationService.addContactRequest(contactRequest);

    return contactMapper.from(receiverPublicId, savedRequest);
  }

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public RestContactRequestList getAllRequest(Pageable pageable) {

    ContactRequestList requests = socialApplicationService.viewListContactRequest(pageable);
    ;

    return contactMapper.from(requests);
  }

  @GetMapping("/accept")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void acceptContactRequestForUser(@RequestBody RestAcceptContactRequest request) {
    PublicId requestPubicId = new PublicId(request.getRequestUserId());
    socialApplicationService.acceptContactRequestForUser(requestPubicId);
  }

  @GetMapping("/{requestId}/accept")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void acceptContactRequestWithId(@PathVariable("requestId") UUID reuqestPublicId) {
    PublicId requestId = new PublicId(reuqestPublicId);
    socialApplicationService.acceptContactRequestForRequest(requestId);
  }

  // @GetMapping("/decliend")
  // @ResponseStatus(HttpStatus.ACCEPTED)
  // public void acceptContactRequest(@RequestBody RestAcceptContactRequest
  // request) {
  // PublicId requestPubicId = new PublicId(request.getRequestUserId());
  // socialApplicationService.acceptContactRequest(requestPubicId);
  // }

}
