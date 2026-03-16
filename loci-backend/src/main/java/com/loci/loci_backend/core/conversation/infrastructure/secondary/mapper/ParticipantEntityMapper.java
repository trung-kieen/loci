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

package com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;

import lombok.RequiredArgsConstructor;

@SecondaryMapper
@RequiredArgsConstructor
public class ParticipantEntityMapper implements DomainEntityMapper<Participant, ConversationParticipantEntity> {
  private final MapStructParticipantEntityMapper mapstruct;

  @Override
  public Participant toDomain(ConversationParticipantEntity entity) {
    // Missing information about conversation
    return mapstruct.toDomain(entity);
  }

  public Participant toDomain(ConversationParticipantEntity entity, ConversationEntity conversationEntity) {
    return mapstruct.toDomain(entity, conversationEntity);
  }


  @Override
  public ConversationParticipantEntity from(Participant domainObject) {
    return mapstruct.from(domainObject);
  }

}
