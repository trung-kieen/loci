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

package com.loci.loci_backend.core.identity.domain.service;

import static org.mockito.Mockito.verify;

import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.repository.GroupPresenceNotifier;
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.repository.GroupPresenceIdTranslator;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceNotifier;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
import com.loci.loci_backend.core.messaging.domain.repository.ForwardIdTranslator;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Presence Indicator")
public class PresenceIndicatorTest {
  @Mock
  UserPresenceRepository userPresenceRepository;
  @Mock
  ConversationRepository conversationRepository;
  @Mock
  GroupPresenceNotifier groupPresenceNotifier;
  @Mock
  UserPresenceNotifier userPresenceNotifier;

  @Mock
  GroupPresenceIdTranslator groupIdTranslator;

  @Mock
  ForwardIdTranslator forwardIdTranslator;


  PresenceIndicator presenceIndicator;

  // fixed mock data for test
  PresenceId presenceId;

  // group user belong to
  GroupConversationPresenceId groupA;
  GroupConversationPresenceId groupB;

  Set<GroupConversationPresenceId> twoGroups;

  GroupSubscriberId subscriberA;
  GroupSubscriberId subscriberB;

  @BeforeEach
  void setUp() {
    // inject mock port
    presenceIndicator = new PresenceIndicator(userPresenceRepository, conversationRepository, groupPresenceNotifier,
        userPresenceNotifier, groupIdTranslator, forwardIdTranslator);

    presenceId = new PresenceId(new PublicId(UUID.randomUUID()));
    groupA = new GroupConversationPresenceId(new ConversationId(10L));
    groupB = new GroupConversationPresenceId(new ConversationId(20L));
    twoGroups = Set.of(groupA, groupB);

    subscriberA = new GroupSubscriberId(UUID.randomUUID());
    subscriberB = new GroupSubscriberId(UUID.randomUUID());
  }

  @Nested
  class SetOnline {

    @Test
    @DisplayName("mark user online in repository with provided status")
    void markUserOnlineInRepository() {
      // action
      presenceIndicator.setOnline(presenceId, PresenceStatus.online());

      // assertion service call repository layer
      verify(userPresenceRepository).setOnline(presenceId, PresenceStatus.online());

    }

  }

}
