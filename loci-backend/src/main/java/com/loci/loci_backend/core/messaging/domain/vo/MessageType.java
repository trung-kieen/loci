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
