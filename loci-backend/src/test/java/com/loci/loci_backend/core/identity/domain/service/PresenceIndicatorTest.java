package com.loci.loci_backend.core.identity.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.conversation.domain.repository.ConversationRepository;
import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.repository.GroupPresenceNotifier;
import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.GroupPresenceIdTranslator;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.domain.vo.PresenceId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;
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
  GroupPresenceIdTranslator idTranslator;

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
        idTranslator);

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
      stubGroupsForUser(twoGroups);
      stubMemberForGroup();
      subTranslator();

      // action
      presenceIndicator.setOnline(presenceId, PresenceStatus.online());

      // assertion service call repository layer
      verify(userPresenceRepository).setOnline(presenceId, PresenceStatus.online());

    }

    @Test
    @DisplayName("broadcast status update when user presence change")
    void broadcastsWhenUserComesBackOnline() {
      UserPresence offlineBefore = UserPresence.offline(presenceId);
      when(userPresenceRepository.getStatus(presenceId)).thenReturn(offlineBefore);
      assertEquals(offlineBefore.isStatusDifference(PresenceStatus.online()), true);
      // when(offlineBefore.isStatusDifference(PresenceStatus.online())).thenReturn(true);
      stubGroupsForUser(twoGroups);
      stubMemberForGroup();
      subTranslator();
      presenceIndicator.heatbeat(presenceId, PresenceStatus.online());
      verify(groupPresenceNotifier, times(2)).boardcastPresenceChange(any(), any());

    }
  }

  private void stubGroupsForUser(Set<GroupConversationPresenceId> groups) {
    when(conversationRepository.getConversationOfPresence(presenceId))
        .thenReturn(groups);
  }

  public void subTranslator() {
    when(idTranslator.toGroupSubscriberId(groupA)).thenReturn(subscriberA);
    when(idTranslator.toGroupSubscriberId(groupB)).thenReturn(subscriberB);
  }

  public void stubMemberForGroup() {
    // return test presence user will belong to group A and B
    when(conversationRepository.getMemberPresenceIds(groupA.value())).thenReturn(Set.of(presenceId));
    when(conversationRepository.getMemberPresenceIds(groupB.value())).thenReturn(Set.of(presenceId));
    when(userPresenceRepository.getMultipleStatus(any())).thenReturn(Map.of());
  }

}
