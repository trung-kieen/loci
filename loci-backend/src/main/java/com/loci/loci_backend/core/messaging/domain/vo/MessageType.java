package com.loci.loci_backend.core.messaging.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageType {

  TEXT("text"),
  FILE("file"),
  IMAGE("image"),
  VIDEO("video");

  @JsonValue
  private String value;

  private MessageType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  public static MessageType fromMimeTypeWithWildcards(String mimeType) {
    if (mimeType == null || mimeType.isBlank()) {
      return FILE;
    }

    String type = mimeType.toLowerCase().split(";", 2)[0].trim();

    if (type.startsWith("text/"))
      return TEXT;
    if (type.startsWith("image/"))
      return IMAGE;
    if (type.startsWith("video/"))
      return VIDEO;
    if (type.startsWith("audio/"))
      return FILE;

    return FILE;
  }

}
