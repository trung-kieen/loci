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

package com.loci.loci_backend.core.identity.domain.vo;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;



public record ProfilePublicId(String value) {

  public static ProfilePublicId from(String email) {
    return new ProfilePublicId(email);
  }
  public static PublicId toPublicId(ProfilePublicId profileId)  {
    return PublicId.of(profileId.value());
  }
  public static Username toUserName(ProfilePublicId profileId)  {
    return Username.from(profileId.value());
  }
}
