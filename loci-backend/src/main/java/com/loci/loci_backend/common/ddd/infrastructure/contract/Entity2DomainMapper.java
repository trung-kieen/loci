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
