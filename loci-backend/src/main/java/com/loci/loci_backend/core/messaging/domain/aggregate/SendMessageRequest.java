package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;
import com.loci.loci_backend.core.messaging.domain.vo.MessageStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;

@Data
public class SendMessageRequest {

  private MessageContent content;

  private PublicId conversationPublicId;

  private Instant sentAt;

  private MessageStatus status;

  private PublicId replyToMessagePublicId;

  @Builder(style = BuilderStyle.STAGED)
  public SendMessageRequest(MessageContent content, PublicId conversationPublicId, Instant sentAt, MessageStatus status,
      PublicId replyToMessagePublicId) {
    this.content = content;
    this.conversationPublicId = conversationPublicId;
    this.sentAt = sentAt;
    this.status = status;
    this.replyToMessagePublicId = replyToMessagePublicId;
  }

}
