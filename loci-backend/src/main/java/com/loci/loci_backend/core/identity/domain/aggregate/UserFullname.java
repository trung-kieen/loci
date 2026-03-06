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

package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFullname implements ValueObject<String> {
  private UserFirstname firstname;
  private UserLastname lastname;

  public UserFullname(UserFullname otherVO) {
    this.firstname = otherVO.getFirstname();
    this.lastname = otherVO.getLastname();

  }

  public static UserFullname from(UserFirstname firstname, UserLastname lastname) {
    return UserFullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }

  public static UserFullname from(UserLastname lastname, UserFirstname firstname) {
    return UserFullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }

  public String value() {
    return firstname.value() + ' ' + lastname.value();
  }

}
