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

package com.loci.loci_backend.core.conversation.infrastructure.secondary.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum ConversationTypeEnum {
  ONE_TO_ONE("one_to_one"),
  GROUP("group");

  @JsonValue
  private String value;

  private ConversationTypeEnum(String value) {
    this.value = value;
  }
  public String value(){
    return this.value;
  }

}
