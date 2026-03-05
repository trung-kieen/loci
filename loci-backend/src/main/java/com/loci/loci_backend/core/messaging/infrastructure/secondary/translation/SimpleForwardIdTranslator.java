package com.loci.loci_backend.core.messaging.infrastructure.secondary.translation;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;
import com.loci.loci_backend.core.groups.infrastructure.secondary.repository.JpaGroupRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.repository.CacheUserIdRepository;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SimpleForwardIdTranslator implements ForwardIdTranslator {

  private final JpaGroupRepository groupRepository;
  private final CacheUserIdRepository userIdRepository;

  @Override
  public UserSubcriberId toPrivateSubscriberId(Participant targetReceiver) {
    UUID userPublicId = userIdRepository.getByDbId(targetReceiver.getUserId().value());
    return new UserSubcriberId(userPublicId);
  }

  @Override
  public GroupSubscriberId toGroupSubscriberId(Conversation conversation) {
    GroupEntity group = groupRepository.findByConversationId(conversation.getId().value())
        .orElseThrow(EntityNotFoundException::new);
    return new GroupSubscriberId(group.getPublicId());
  }

}
