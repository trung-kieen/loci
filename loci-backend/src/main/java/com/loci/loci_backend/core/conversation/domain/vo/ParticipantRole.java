package com.loci.loci_backend.core.conversation.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;

public record ParticipantRole(@JsonProperty ParticipantRoleEnum value) implements ValueObject<ParticipantRoleEnum> {

  public static ParticipantRole of(String value) {
    return NullSafe.getIfPresent(value, (v) -> new ParticipantRole(ParticipantRoleEnum.of(value)));
  }

  public static ParticipantRole admin() {
    return new ParticipantRole(ParticipantRoleEnum.ADMIN);
  }

  public static ParticipantRole member() {
    return new ParticipantRole(ParticipantRoleEnum.MEMBER);
  }
}
