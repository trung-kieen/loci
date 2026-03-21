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

package com.loci.loci_backend.common.ddd.domain.contract;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {

  T value();

  // boolean isEmpty();
  //
  // default String asString() {
  // return Objects.toString(value());
  // }

  public static boolean isPresent(ValueObject<?> object) {
    if (object == null || object.value() == null) {
      return false;
    }
    return true;
  }

  public static boolean isAbsent(ValueObject<?> object) {
    return !isPresent(object);

  }

}
