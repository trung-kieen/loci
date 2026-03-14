package com.loci.loci_backend.core.groups.infrastructure.primary.payload;

import java.util.UUID;

import com.loci.loci_backend.core.conversation.domain.vo.ParticipantRoleEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestGroupParticipant {
  private UUID userId;
  private String fullname;
  private String username;
  private String avatarUrl;
  // TODO: use the JSON value for enum
  private ParticipantRoleEnum role;
  // private UserPresence status;
}
