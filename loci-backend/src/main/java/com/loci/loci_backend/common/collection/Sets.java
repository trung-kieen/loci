package com.loci.loci_backend.common.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Sets {

  private Sets() {
  }

  public static <T, K> Set<T> byField(Collection<K> items, Function<K, ? extends T> fieldExtractor) {

    if (items == null || items.isEmpty()) {
      return new HashSet<>();
    }

    return items.stream()
        .map(fieldExtractor)
        .collect(Collectors.toSet());
  }
}
