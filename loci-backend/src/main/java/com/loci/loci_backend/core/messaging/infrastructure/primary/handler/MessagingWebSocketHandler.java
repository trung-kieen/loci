package com.loci.loci_backend.core.messaging.infrastructure.primary.handler;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessagingWebSocketHandler {

  // NOTE: use Ws like preffix for Rest or Socket
  // Ws...Request/Event
  @MessageMapping("/individual/send")
  @SendToUser("/queue/individual/receive")
  public void sendIndividual() { // Use custom object to send message
    // String senderId = headers.getUser().getName();
    // SendIndividualMessageCommand command = mapper.toCommand(payload, senderId);
    // Message message = sendIndividualUseCase.execute(command);
    //
    // // Send to recipient
    // messagingTemplate.convertAndSendToUser(
    // recipientUsername,
    // "/queue/individual/receive",
    // mapper.toIndividualReceivePayload(message)
    // );
    //
    // return mapper.toIndividualReceivePayload(message); // sender also gets
    // confirmation
    //
    throw new NotImplementedException();
  }

  @MessageMapping("/individual/seen")
  @SendToUser("/queue/individual/update_read_recept")
  public void markIndividualSeen( // ReadReceiptResponse
  // @Payload SeenRequest payload,
  // Principal user
  ) {
    // MarkSeenCommand command = mapper.toSeenCommand(payload, user.getName());
    // ReadReceipt receipt = markSeenUseCase.execute(command);
    //
    // // notify the other party
    // messagingTemplate.convertAndSendToUser(
    // otherUser,
    // "/queue/individual/update_read_recept",
    // mapper.toReadReceiptPayload(receipt));
    //
    // return mapper.toReadReceiptPayload(receipt);
    throw new NotImplementedException();
  }

  @MessageMapping("/individual/react")
  public void reactToIndividual(
  // @Payload ReactRequest payload, Principal user
  ) {
    // ReactCommand command = mapper.toReactCommand(payload, user.getName());
    // Reaction reaction = reactUseCase.execute(command);
    //
    // // notify conversation participants
    // messagingTemplate.convertAndSendToUser(...);

  }

  // Same pattern for group/*
  @MessageMapping("/group/send")
  @SendTo("/topic/group/{groupId}") // use @DestinationVariable
  public void sendGroup() { // GroupMessageResponse
    throw new NotImplementedException();
  }

}
