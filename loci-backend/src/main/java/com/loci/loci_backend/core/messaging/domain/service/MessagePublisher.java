package com.loci.loci_backend.core.messaging.domain.service;

import java.util.List;

import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.messaging.domain.aggregate.Message;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessagePublisher {

  public void sendPrivateMessage(Conversation conversation, Participant targetReciver, Message message) {

  }

  public void sendGroupMessage(Conversation conversation, List<Participant> groupParticipants, Message message) {

  }

}
