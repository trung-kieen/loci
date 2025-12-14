package com.loci.loci_backend.common.mapper;

import java.util.Set;
import java.util.stream.Collectors;

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
}
