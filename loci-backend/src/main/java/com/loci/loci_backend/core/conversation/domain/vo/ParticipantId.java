package com.loci.loci_backend.core.conversation.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record ParticipantId(Long value) implements ValueObject<Long> {
}
