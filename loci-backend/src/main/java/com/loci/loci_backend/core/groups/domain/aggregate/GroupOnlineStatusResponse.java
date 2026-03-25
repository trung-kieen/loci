package com.loci.loci_backend.core.groups.domain.aggregate;

import java.time.Instant;
import java.util.Set;

import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;

import lombok.Data;

@Data
public class GroupOnlineStatusResponse {
  private final Set<UserPresence> userPresences;
  private final Instant fetchedAt;
}
