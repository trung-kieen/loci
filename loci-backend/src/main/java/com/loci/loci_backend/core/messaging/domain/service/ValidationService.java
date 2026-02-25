package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ValidationService {

  private void validateInput() {
  }

  public void validateMessageContent(MessageContent content) {
    // TODO:
    // Assert.field("message content", content)
  }

}
