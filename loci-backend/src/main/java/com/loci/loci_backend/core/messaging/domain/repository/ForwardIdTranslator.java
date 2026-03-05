package com.loci.loci_backend.core.messaging.domain.repository;

import com.loci.loci_backend.core.conversation.domain.aggregate.Conversation;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

public interface ForwardIdTranslator {

  /**
   * {@link} JWSAuthentication
   */
  public UserSubcriberId toPrivateSubscriberId(Participant targetReceiver);

  public GroupSubscriberId toGroupSubscriberId(Conversation conversation);

}
