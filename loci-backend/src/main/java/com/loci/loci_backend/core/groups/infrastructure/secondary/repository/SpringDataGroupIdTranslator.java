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

package com.loci.loci_backend.core.groups.infrastructure.secondary.repository;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.groups.domain.repository.GroupIdTranslator;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataGroupIdTranslator implements GroupIdTranslator {@Override
  public GroupId toInternal(PublicId publicId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toInternal'");
  }

  @Override
  public PublicId toPublic(GroupId internalId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toPublic'");
  }

}
