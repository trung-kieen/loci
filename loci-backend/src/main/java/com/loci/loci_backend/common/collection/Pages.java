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

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class Pages {

  private Pages() {
  }

  public static <S, T> Page<T> map(Page<S> sourcePage, Function<S, T> converter) {
    if (sourcePage == null) {
      return null;
    }
    List<T> content = sourcePage.getContent().stream().map(converter).toList();
    return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
  }

  public static <S, T> Page<T> mapOrNull(Page<S> sourcePage, Map<S, T> converter) {
    if (sourcePage == null) {
      return null;
    }
    List<T> content = sourcePage.getContent().stream().map(e -> converter.getOrDefault(e, null)).toList();
    return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
  }

  /**
   * Map via hashmap or throw error
   */
  public static <S, T> Page<T> map(Page<S> sourcePage, Map<S, T> converter) {
    if (sourcePage == null) {
      return null;
    }
    List<T> content = sourcePage.getContent().stream().map(e -> converter.get(e)).toList();
    return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
  }
}
