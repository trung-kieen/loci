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

package com.loci.loci_backend.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loci.loci_backend.common.user.domain.vo.UserFirstname;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NullSafeTest {

  private final String value = "Hello";

  /**
   * Given: UserFirstName.class, "Hello"
   * Expect: .value().equals("Hello")
   */
  @Test
  @DisplayName("Should create value object record if value is present")
  public void testConstructAValidValueObject() throws Exception {
    UserFirstname instance = NullSafe.constructOrNull(UserFirstname.class, value);

    NullSafe.constructOrNull(UserFirstname.class, value);
    UserFirstname normalConstruct = new UserFirstname(value);

    assertEquals(instance, normalConstruct);
  }

  /**
   * Given: null, "Hello"
   * Expect: throw an Exception
   */

  @Test
  @DisplayName("Should throw if not provide class")
  public void testThrowIfConstructNullClass() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> {
      NullSafe.constructOrNull(null, value);
    });

  }

  /**
   * Given: UserFirstName.class, null
   * Expect: null
   */

  @Test
  public void testNullIfNotPresentValue() throws Exception {

    UserFirstname  instance = NullSafe.constructOrNull(UserFirstname.class, null);
    assertEquals(instance, null);

  }

  /**
   * Given: UserFirstName.class, 123 // Wrong type in constructor
   * Expect: throw an Exception
   */

  // @Test
  // public void testThrowIfProvideInvalidConstructorArguemnt() throws Exception {
  //
  //   assertThrows(Exception.class, () -> {
  //     NullSafe.constructOrNull(UserFirstname.class, 123);  // Expect String  for constructor
  //   });
  // }
}
