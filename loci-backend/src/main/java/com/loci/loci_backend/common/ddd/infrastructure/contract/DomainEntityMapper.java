package com.loci.loci_backend.common.ddd.infrastructure.contract;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

/**
 * Convert from domain (D) to entity (E) and reverse
 */
public interface DomainEntityMapper<D, E> {
  D toDomain(E entity);

  E from(D domainObject);

  default Set<D> toDomain(Set<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toSet());
  }

  default Set<E> from(Set<D> domainObjects) {
    return domainObjects.stream().map(this::from).collect(Collectors.toSet());
  }

  default List<E> from(List<D> domainObjects) {
    return domainObjects.stream().map(this::from).collect(Collectors.toList());
  }

  default Page<D> toDomain(Page<E> entities) {
    return entities.map(this::toDomain);
  }

  default List<D> toDomain(List<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toList());
  }
  default Set<D> toDomain(Collection<E> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toSet());
  }
}
