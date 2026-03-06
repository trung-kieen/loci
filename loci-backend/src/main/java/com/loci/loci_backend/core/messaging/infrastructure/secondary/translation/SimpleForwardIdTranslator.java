package com.loci.loci_backend.core.messaging.infrastructure.secondary.translation;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.repository.UserRepository;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
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
  private final JpaUserRepository userRepository;

  @Override
  public UserSubcriberId toPrivateSubscriberId(Participant targetReceiver) {
    UserEntity user = userRepository.findById(targetReceiver.getUserId().value())
        .orElseThrow(EntityNotFoundException::new);
    return new UserSubcriberId(user.getPublicId());
  }

  @Override
  public GroupSubscriberId toGroupSubscriberId(Conversation conversation) {
    GroupEntity group = groupRepository.findByConversationId(conversation.getId().value())
        .orElseThrow(EntityNotFoundException::new);
    return new GroupSubscriberId(group.getPublicId());
  }

}
