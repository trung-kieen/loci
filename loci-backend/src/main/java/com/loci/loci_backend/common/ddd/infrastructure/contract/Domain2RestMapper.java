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
