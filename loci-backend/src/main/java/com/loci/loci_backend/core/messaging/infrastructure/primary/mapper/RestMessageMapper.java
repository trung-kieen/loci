package com.loci.loci_backend.core.messaging.infrastructure.primary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestMessageMapper {

  private final MapStructRestMessageMapper mapstruct;

  public RestMessage from(Message domain) {
    return mapstruct.from(domain);
  }
}
