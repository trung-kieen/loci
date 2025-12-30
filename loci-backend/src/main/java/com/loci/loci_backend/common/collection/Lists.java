package com.loci.loci_backend.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lists {

  private Lists() {
  }


  /**
   * Extract list from origin object list by fieldExtractor function
   */
  public static <T, K> List<T> byField(Collection<K> items, Function<? super K, ? extends T> fieldExtractor) {
    if (items == null || items.isEmpty()) {
      return new ArrayList<>();
    }

    return items.stream()
        .map(fieldExtractor)
        .collect(Collectors.toList());
  }


  public static <T> List<List<T>> partition(List<T> list, int size) {
    List<List<T>> partitions = new ArrayList<>();
    for (int i = 0; i < list.size(); i += size) {
      partitions.add(list.subList(i, Math.min(i + size, list.size())));
    }
    return partitions;
  }
}
