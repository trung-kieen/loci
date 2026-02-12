package com.loci.loci_backend.core.messaging.infrastructure.primary.payload;

import com.loci.loci_backend.core.conversation.infrastructure.primary.payload.RestMessage;

public class RestConversationMessage extends RestMessage {
  private boolean isOwner;

  public boolean isOwner() {
    return isOwner;
  }

  public void setOwner(boolean isOwner) {
    this.isOwner = isOwner;
  }

}
