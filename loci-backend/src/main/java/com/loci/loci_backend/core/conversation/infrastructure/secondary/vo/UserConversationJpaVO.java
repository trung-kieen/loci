package com.loci.loci_backend.core.conversation.infrastructure.secondary.vo;

import java.util.UUID;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration.ConversationTypeEnum;

public record UserConversationJpaVO(
    Long conversationId,
    UUID publicId,
    ConversationTypeEnum type,
    Long conversationLastMessageId,
    Long userLastReadMessageId
) {

}
