package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestFriendshipUpdatedResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendResource {

  private final SocialApplicationService socialApplicationService;

  @DeleteMapping("/{friendId}")
  @ResponseStatus(HttpStatus.OK)
  public RestFriendshipUpdatedResponse removeFriendContact(@PathVariable("friendId") UUID userId) {
    PublicId userPublicId = new PublicId(userId);
    socialApplicationService.removeContactConnection(userPublicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.notConnected());
  }

}
