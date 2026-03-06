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

package com.loci.loci_backend.core.social.domain.aggregate;

import java.util.List;

import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ContactRequestList {
  private Page<ContactRequest> contacts;
  private List<UserSummary> userSummaries;
  @Builder(style = BuilderStyle.STAGED)
  public ContactRequestList(Page<ContactRequest> contacts, List<UserSummary> userSummaries) {
    this.contacts = contacts;
    this.userSummaries = userSummaries;
  }


}
