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

package com.loci.loci_backend.common.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sets {

  private Sets() {
  }

  public static <T, K> Set<T> byField(Collection<K> items, Function<K, ? extends T> fieldExtractor) {

    if (items == null || items.isEmpty()) {
      return new HashSet<>();
    }

    return items.stream()
        .map(fieldExtractor)
        .collect(Collectors.toSet());
  }

  public static <T> Set<T> difference(Collection<T> a, Collection<T> b) {
    Set<T> substractions = new HashSet<>(a);
    substractions.removeAll(b);
    return substractions;
  }
}
