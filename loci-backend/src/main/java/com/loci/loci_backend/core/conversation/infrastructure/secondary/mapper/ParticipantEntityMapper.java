package com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.DomainEntityMapper;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
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
