package com.loci.loci_backend.core.social.infrastructure.primary.payload;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBlockedUserList {
  private Page<RestBlockedUser> users;
}
