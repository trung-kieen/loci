package com.loci.loci_backend.core.conversation.domain.repository;

import java.util.Collection;
import java.util.List;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.ConversationSearchCriteria;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.conversation.domain.aggregate.UserConversation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantRepository {

  List<Participant> addParticipants(Conversation conversation, Collection<Participant> participants);

  Page<UserConversation> getConversationsUserJoined(User user, ConversationSearchCriteria criteria, Pageable pageable);

}
