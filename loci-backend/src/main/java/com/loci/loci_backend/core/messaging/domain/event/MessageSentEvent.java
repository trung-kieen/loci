package com.loci.loci_backend.core.messaging.domain.event;

import com.loci.loci_backend.common.ddd.domain.contract.DomainEvent;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

public record MessageSentEvent(
    Message message, Conversation conversation, User sender)
    implements DomainEvent {
}
