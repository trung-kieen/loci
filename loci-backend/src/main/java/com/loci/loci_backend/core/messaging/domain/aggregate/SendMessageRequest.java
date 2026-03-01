package com.loci.loci_backend.core.messaging.domain.aggregate;

import java.util.Optional;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.messaging.domain.vo.MessageContent;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendMessageRequest {

  private MessageContent content;

  private PublicId conversationPublicId;

  private Optional<PublicId> replyToMessagePublicId;

  private Optional<Attachment> attachment;

  @Builder(style = BuilderStyle.STAGED)
  public SendMessageRequest(MessageContent content, PublicId conversationPublicId,
      Optional<PublicId> replyToMessagePublicId, Attachment attachment) {
    this.content = content;
    this.conversationPublicId = conversationPublicId;
    this.replyToMessagePublicId = replyToMessagePublicId;
    this.attachment = Optional.ofNullable(attachment);
  }

}
