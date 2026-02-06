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
