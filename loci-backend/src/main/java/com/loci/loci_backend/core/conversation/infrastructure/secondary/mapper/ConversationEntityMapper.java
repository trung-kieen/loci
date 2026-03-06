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

import java.util.Collection;
import java.util.Set;

import com.loci.loci_backend.common.ddd.infrastructure.contract.DomainEntityMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SecondaryMapper
public class ConversationEntityMapper implements DomainEntityMapper<Conversation, ConversationEntity> {
  private final MapStructConversationEntityMapper mapstruct;
  private final ParticipantEntityMapper participantMapper;

  @Override
  public Conversation toDomain(ConversationEntity entity) {
    return mapstruct.toDomain(entity);
  }

  public Conversation toDomain(ConversationEntity entity,
      Collection<ConversationParticipantEntity> participantEntities) {
    Conversation conversation = mapstruct.toDomain(entity);
    Set<Participant> participants = participantMapper.toDomainSet(participantEntities);

    conversation.setParticipants(participants);

    return conversation;
  }

  @Override
  public ConversationEntity from(Conversation domainObject) {
    return mapstruct.from(domainObject);
  }

  public UserConversation toDomain(UserConversationJpaVO vo) {
    return mapstruct.toDomain(vo);
  }

  public GroupChatInfo toDomain(GroupConversationMetadataJpaVO vo) {
    return mapstruct.toDomain(vo);
  }

}
