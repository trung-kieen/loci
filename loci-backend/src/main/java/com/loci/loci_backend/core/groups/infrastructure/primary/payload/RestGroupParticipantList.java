package com.loci.loci_backend.core.groups.infrastructure.primary.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestGroupParticipantList {
  private List<RestGroupParticipant> participants;
}
