package com.loci.loci_backend.core.conversation.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;
import com.loci.loci_backend.core.messaging.domain.aggregate.GroupChatInfo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructConversationEntityMapper {

  @Mapping(target = "messages", ignore = true)
  @Mapping(target = "participants", ignore = true)
  public Conversation toDomain(ConversationEntity conversation);

  public ConversationEntity from(Conversation domainObject);

  public UserConversation toDomain(UserConversationJpaVO vo);

  public GroupChatInfo toDomain(GroupConversationMetadataJpaVO vo);

}
