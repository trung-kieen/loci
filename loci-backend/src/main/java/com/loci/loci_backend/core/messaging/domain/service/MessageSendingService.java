package com.loci.loci_backend.core.messaging.domain.service;

public class MessageSendingService {

  public void sendMessage() {
    // get conversation

    // get sender

    // validate content of message or throw bad request

    // validate user can message to conversation (direct message / group message)

    // save message as this conversation

    // mark message as latest for this conversation

    // mark message as latest for this sender (current user)

    // forward message for single user / user in group via message queue (forwardMessage)

  }

  /**
   * retry to forward the message to target user id and handle the fail if needed
   */
  public void forwardMessage() {

    // get opponent user or group of user

    // determine unicast or multicast message

    // forward message via messaging service (rabbit mq)

    // forward notification to target receiver too via notification service

  }

  void trackMessage() {
  }

}
