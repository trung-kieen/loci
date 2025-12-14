package com.loci.loci_backend.common.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

public interface DomainRestMapper<D, R> {
  R from(D domain);

  D toDomain(R restModel);

  default Set<R> from(Set<D> domainSet) {
    if (domainSet == null) {
      return null;
    }
    return domainSet.stream()
        .map(this::from)
        .collect(Collectors.toSet());
  }

  default Set<D> toDomain(Set<R> restSet) {
    if (restSet == null) {
      return null;
    }
    return restSet.stream()
        .map(this::toDomain)
        .collect(Collectors.toSet());
  }


  default Page<R> from(Page<D> domainPage) {
    if (domainPage == null) {
      return null;
    }
    return domainPage.map(this::from);
  }

  default Page<D> toDomain(Page<R> restPage) {
    if (restPage == null) {
      return null;
    }
    return restPage.map(this::toDomain);
  }

}
