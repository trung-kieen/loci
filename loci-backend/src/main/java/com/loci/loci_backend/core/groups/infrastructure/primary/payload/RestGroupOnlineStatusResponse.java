package com.loci.loci_backend.core.groups.infrastructure.primary.payload;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestGroupOnlineStatusResponse {

  private List<UUID> onlineUserIds;
  private Instant fetchedAt;

  public static RestGroupOnlineStatusResponse EMPTY() {
    var dto = new RestGroupOnlineStatusResponse();
    dto.onlineUserIds = new ArrayList<>();
    dto.fetchedAt = Instant.now();
    return dto;
  }

}
