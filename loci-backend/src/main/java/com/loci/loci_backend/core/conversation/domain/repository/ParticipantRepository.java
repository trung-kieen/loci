package com.loci.loci_backend.core.conversation.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;
import com.loci.loci_backend.core.conversation.domain.vo.ParticipantCount;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRepository {

  List<Participant> addParticipants(Conversation conversation, Collection<Participant> participants);

  Page<UserConversation> getConversationsUserJoined(User user, ConversationSearchCriteria criteria, Pageable pageable);

  boolean isParticipantInConversation(User user, Conversation conversation);

  Participant findRecipientOfUserInConversation(User requestUser, Conversation conversation);

  ParticipantCount countConversationMember(Conversation conversation);

  Set<UserDBId> getGroupMemberIds(GroupId groupId);

}
