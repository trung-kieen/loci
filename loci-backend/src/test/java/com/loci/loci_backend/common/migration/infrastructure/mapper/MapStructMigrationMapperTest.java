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

package com.loci.loci_backend.common.migration.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    MapStructMigrationMapperImpl.class, // the generated implementation
})
public class MapStructMigrationMapperTest {

  @Autowired
  private MapStructMigrationMapper mapper;

  @Test
  public void testMappingValidUser() throws Exception {

    User user = new User();
    UserFirstname firstname = new UserFirstname("about");
    UserLastname lastname = new UserLastname("company");
    UserEmail email = new UserEmail("exampleemail@gmail.com");
    Username username = new Username("enjoy");
    user.setFirstname(firstname);
    user.setLastname(lastname);
    user.setEmail(email);
    user.setUsername(username);

    KeycloakUser keycloakUser = mapper.toKeycloakUser(user);
    assertEquals(keycloakUser.getFirstName(), firstname);
    assertEquals(keycloakUser.getLastName(), lastname);
    assertEquals(keycloakUser.getEmail(), email);
    assertEquals(keycloakUser.getUsername(), username);
    assertEquals(keycloakUser.getUsername(), username);

  }

}
