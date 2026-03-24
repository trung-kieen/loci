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

package com.loci.loci_backend.core.messaging.infrastructure.secondary.translation;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SimpleForwardIdTranslator implements ForwardIdTranslator {

  private final JpaUserRepository userRepository;

  @Override
  public UserSubcriberId toPrivateSubscriberId(UserDBId userId) {
    UserEntity user = userRepository.findById(userId.value())
        .orElseThrow(EntityNotFoundException::new);
    return new UserSubcriberId(user.getPublicId());
  }

  @Override
  public GroupSubscriberId toGroupSubscriberId(Conversation conversation) {
    return new GroupSubscriberId(conversation.getPublicId().value());
    // GroupEntity group =
    // groupRepository.findByConversationId(conversation.getId().value())
    // .orElseThrow(EntityNotFoundException::new);
    // return new GroupSubscriberId(group.getPublicId());
  }

  @Override
  public UserSubcriberId toPrivateSubscriberId(PublicId targetReceiver) {
    return new UserSubcriberId(targetReceiver.value());
  }

  @Override
  public UserSubcriberId toPrivateSubscriberId(PresenceId targetReceiverPresenceId) {
    PublicId userPublicId = targetReceiverPresenceId.value();
    return this.toPrivateSubscriberId(userPublicId);
  }

}
