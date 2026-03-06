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

// NOTE: Decided separate sending and receive message instead use in one place

package com.loci.loci_backend.core.messaging.infrastructure.primary.handler;

import java.security.Principal;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MessagingWebSocketHandler {
  private final SimpMessageSendingOperations messageTemplate;

  // NOTE: use Ws like preffix for Rest or Socket
  // Ws...Request/Event
  @MessageMapping("/individual.send")
  // @SendToUser("/queue/individual/receive")
  public String sendIndividual(@Payload String body, Principal principal, SimpMessageHeaderAccessor headerAccessor) {

    String currentUsername = headerAccessor.getUser().getName();
    System.out.println(currentUsername);
    System.out.println(principal.getName());
    System.out.println(body);
            String senderId = principal.getName();
            System.out.println("Sender principal: " + senderId);
    String userId = "e8fe4a7f-619c-4d14-a25f-fe3de4b90549";
    if (!userId.equals(principal.getName())){
      throw new RuntimeException("Princial note match userid");
    }
    if (!userId.equals(currentUsername)){
      throw new RuntimeException("Username note match userid");
    }
    messageTemplate.convertAndSendToUser(userId, "/queue/individual.receive", body);

    return principal.getName();
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
    // throw new NotImplementedException();
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
