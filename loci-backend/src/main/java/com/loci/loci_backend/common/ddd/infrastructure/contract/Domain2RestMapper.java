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

package com.loci.loci_backend.common.ddd.infrastructure.contract;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface Domain2RestMapper<D, R> {

  R from(D domain);

  default Set<R> from(Set<D> domainSet) {
    if (domainSet == null) {
      return null;
    }
    return domainSet.stream()
        .map(this::from)
        .collect(Collectors.toSet());
  }
  default List<R> from(List<D> domainSet) {
    if (domainSet == null) {
      return null;
    }
    return domainSet.stream()
        .map(this::from)
        .collect(Collectors.toList());
  }

  default Page<R> from(Page<D> domainPage) {
    if (domainPage == null) {
      return null;
    }

    List<R> mappedContent = domainPage.getContent()
        .stream()
        .map(this::from)
        .collect(Collectors.toList());

    return new PageImpl<>(mappedContent,
        domainPage.getPageable(),
        domainPage.getTotalElements());
  }
}
