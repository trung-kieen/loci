/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.messaging.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
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
