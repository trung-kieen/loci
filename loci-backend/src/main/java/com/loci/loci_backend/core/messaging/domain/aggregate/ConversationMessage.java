package com.loci.loci_backend.core.messaging.domain.aggregate;

public class ConversationMessage extends Message {
  private boolean isOwner;

  public boolean isOwner() {
    return isOwner;
  }

  public void setOwner(boolean isOwner) {
    this.isOwner = isOwner;
  }

}
