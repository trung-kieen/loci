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

package com.loci.loci_backend.core.groups.infrastructure.secondary.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import com.loci.loci_backend.core.groups.domain.vo.GroupConversationPresenceId;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.STOMPUserPresence;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Getter;

@Getter
public class STOMPGroupPresence implements Serializable {

  private final GroupConversationPresenceId groupPresenceId;
  private final Map<UUID, STOMPUserPresence> participantPresences;
  @Builder(style = BuilderStyle.STAGED)
  public STOMPGroupPresence(GroupConversationPresenceId groupPresenceId,
      Map<UUID, STOMPUserPresence> participantPresences) {
    this.groupPresenceId = groupPresenceId;
    this.participantPresences = participantPresences;
  }


}
