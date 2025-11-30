package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestContactRequestResponse;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestSendContactRequest;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.el.MethodNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contact-requests")
@RequiredArgsConstructor
class ContactRequestResource {

  // private final SendContactRequestUseCase sendContactRequestUseCase;
  // private final ContactWebMapper mapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RestContactRequestResponse sendRequest(@Valid @RequestBody RestSendContactRequest request,
      Authentication auth) {
    // SendContactRequestCommand command = mapper.toCommand(request,
    // auth.getName());
    // ContactRequest sent = sendContactRequestUseCase.execute(command);
    // return mapper.toResponse(sent);
    throw new NotImplementedException();
  }
}
