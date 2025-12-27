package com.loci.loci_backend.core.conversation.infrastructure.primary.payload;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestUserChatList {
  private Page<RestChat> conversations;
}
