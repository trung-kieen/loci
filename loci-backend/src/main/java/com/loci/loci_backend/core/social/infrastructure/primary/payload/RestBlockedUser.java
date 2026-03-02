package com.loci.loci_backend.core.social.infrastructure.primary.payload;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBlockedUser {

  private UUID userId;
  private String username;
  private String fullname;
  private String profilePictureUrl;


  // TODO: reason

}
