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

package com.loci.loci_backend.core.groups.domain.aggregate;

import java.time.Instant;
import java.util.Set;

import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;

import lombok.Data;

@Data
public class GroupOnlineStatusResponse {
  private final Set<UserPresence> userPresences;
  private final Instant fetchedAt;
}
