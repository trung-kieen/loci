package com.loci.loci_backend.core.messaging.domain.repository;

import com.loci.loci_backend.core.messaging.domain.aggregate.Message;
import com.loci.loci_backend.core.messaging.domain.vo.GroupSubscriberId;
import com.loci.loci_backend.core.messaging.domain.vo.UserSubcriberId;

public interface MessagePublisher {

  void sendInvidualMessage(UserSubcriberId forwardId, Message message);

  void sendGroupMessage(GroupSubscriberId conversationId, Message message);

}
