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

package com.loci.loci_backend.core.conversation.domain.aggregate;

import java.util.Set;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.Validatable;
import com.loci.loci_backend.core.groups.domain.vo.GroupImageUrl;
import com.loci.loci_backend.core.groups.domain.vo.GroupName;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateGroupRequest implements Validatable {

  private GroupName groupName;
  private GroupImageUrl profileImage;
  private Set<PublicId> memberIds;

  @Builder(style = BuilderStyle.STAGED)
  public CreateGroupRequest(GroupName groupName, GroupImageUrl profileImage) {
    this.groupName = groupName;
    this.profileImage = profileImage;
  }

  public void provideMandatoryField() {
    initGroupImageForSignUp();
  }

  private void initGroupImageForSignUp() {
    if (this.profileImage == null || profileImage.value() == null) {
      this.profileImage = GroupImageUrl.random();
    }
  }

  @Override
  public void validate() {
    Assert.field("Member list", memberIds).minSize(2);
  }
}
