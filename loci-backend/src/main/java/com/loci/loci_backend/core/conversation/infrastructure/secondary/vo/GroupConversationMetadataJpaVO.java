package com.loci.loci_backend.core.conversation.infrastructure.secondary.vo;

import java.util.UUID;

public record GroupConversationMetadataJpaVO(
    // conversation
    Long conversationId,
    UUID conversationPublicId,

    // group
    Long groupId,
    UUID groupPublicId,
    String groupName,
    String profileImage,
    Long participantCount
) {
}
