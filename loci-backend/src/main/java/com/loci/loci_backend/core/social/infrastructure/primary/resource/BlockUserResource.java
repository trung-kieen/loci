package com.loci.loci_backend.core.social.infrastructure.primary.resource;

import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.social.application.SocialApplicationService;
import com.loci.loci_backend.core.social.domain.vo.FriendshipStatus;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestFriendshipUpdatedResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockUserResource {

  private final SocialApplicationService socialApplicationService;

  @PostMapping("/{blockUserId}")
  @ResponseStatus(HttpStatus.CREATED)
  public RestFriendshipUpdatedResponse blockUser(@PathVariable("blockUserId") UUID userId) {
    PublicId publicId = new PublicId(userId);
    socialApplicationService.blockUser(publicId);
    return new RestFriendshipUpdatedResponse(FriendshipStatus.blockedOther());

  }

  @DeleteMapping("/{blockUserId}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public RestFriendshipUpdatedResponse unblockUser(@PathVariable("blockUserId") UUID userId) {

    PublicId publicId = new PublicId(userId);
    FriendshipStatus newStatus = socialApplicationService.unblockUser(publicId);
    return new RestFriendshipUpdatedResponse(newStatus);
  }

}
