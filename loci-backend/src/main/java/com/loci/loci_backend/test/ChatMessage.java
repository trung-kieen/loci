package com.loci.loci_backend.test;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    // private String sender;
    private String content;
    // private LocalDateTime timestamp;
}
