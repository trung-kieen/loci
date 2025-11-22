package com.loci.loci_backend.core.user.infrastructure.primary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestProfilePrivacy {
  private boolean lastSeen;
  private String friendRequests;
  private boolean profileVisibility;
}
