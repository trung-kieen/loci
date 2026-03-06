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

import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Pages;

import org.springframework.data.domain.Page;

public interface Rest2DomainMapper<R, D> {

  D toDomain(R restModel);

  default Set<D> toDomain(Set<R> restSet) {
    if (restSet == null) {
      return null;
    }
    return restSet.stream()
        .map(this::toDomain)
        .collect(Collectors.toSet());
  }

  default Page<D> toDomain(Page<R> restPage) {
    if (restPage == null) {
      return null;
    }
    return Pages.map(restPage, this::toDomain);
  }
}
