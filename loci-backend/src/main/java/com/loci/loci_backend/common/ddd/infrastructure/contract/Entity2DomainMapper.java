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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface Entity2DomainMapper<E, D> {

  D toDomain(E entity);

  default Set<D> toDomain(Set<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toSet());
  }

  default Page<D> toDomain(Page<E> entities) {
    if (entities == null){
      return null;
    }
    List<D> mappedContent = entities.getContent()
      .stream().map(this::toDomain).toList();
    return new PageImpl<>(mappedContent, entities.getPageable(), entities.getTotalElements());
  }

  default List<D> toDomain(List<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toList());
  }

  default Set<D> toDomainSet(Collection<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toSet());
  }

  default List<D> toDomainList(Collection<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toList());
  }

}
