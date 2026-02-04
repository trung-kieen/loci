package com.loci.loci_backend.common.ddd.infrastructure.contract;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.collection.Pages;

import org.springframework.data.domain.Page;

public interface Domain2EntityMapper<D, E> {

  E from(D domainObject);

  default Set<E> from(Set<D> domainObjects) {
    return domainObjects.stream().map(this::from).collect(Collectors.toSet());
  }

  default List<E> from(List<D> domainObjects) {
    return domainObjects.stream().map(this::from).collect(Collectors.toList());
  }

  default Page<E> from(Page<D> domainPage) {
    return Pages.map(domainPage, this::from);
  }

}
