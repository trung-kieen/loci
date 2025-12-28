package com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructParticipantEntityMapper {

  @Mapping(source = "createdDate", target = "joinedAt")
  @Mapping(target = "conversationPublicId", ignore = true)
  public Participant toDomain(ConversationParticipantEntity entity);

  @Mapping(source = "participant.createdDate", target = "joinedAt")
  @Mapping(source = "participant.id", target = "id")
  @Mapping(source = "conversation.publicId", target = "conversationPublicId")
  public Participant toDomain(ConversationParticipantEntity participant, ConversationEntity conversation);

  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public ConversationParticipantEntity from(Participant domainObject);

}
