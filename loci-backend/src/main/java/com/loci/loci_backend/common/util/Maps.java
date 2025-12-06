package com.loci.loci_backend.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Maps {

  private Maps() {
    /* utility class */ }

  /**
   * An immutable-view lookup map from a list using the provided key
   * extractor.
   * Throws IllegalStateException if duplicate keys are found (recommended –
   * duplicate IDs are usually a bug).
   *
   * @param items        the list of objects (null-safe)
   * @param keyExtractor function that returns the key (usually obj::getId or
   *                     obj::getCode etc.)
   * @param <T>          type of the objects
   * @param <K>          type of the key (Long, String, UUID, Integer, custom ID
   *                     object, …)
   * @return unmodifiable map for fast O(1) lookup by key
   */
  public static <T, K> Map<K, T> toLookupMap(List<T> items, Function<? super T, ? extends K> keyExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<K, T> map = new HashMap<>(items.size() * 4 / 3 + 1); // good initial capacity

    for (T item : items) {
      K key = keyExtractor.apply(item);
      T previous = map.putIfAbsent(key, item); // Java 8+ atomic put-if-absent
      if (previous != null) {
        throw new IllegalStateException("Duplicate key detected: " + key);
      }
    }

    return Collections.unmodifiableMap(map);
  }


  /** Same as above but uses stream (slightly slower but very readable) */
  public static <T, K> Map<K, T> toLookupMapStream(List<T> items, Function<? super T, ? extends K> keyExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    return items.stream()
        .collect(HashMap::new,
            (m, item) -> {
              K key = keyExtractor.apply(item);
              T old = m.putIfAbsent(key, item);
              if (old != null) {
                throw new IllegalStateException("Duplicate key: " + key);
              }
            },
            HashMap::putAll);
  }

  /** Keeps the last occurrence on duplicate keys instead of throwing */
  public static <T, K> Map<K, T> toLookupMapKeepLast(List<T> items, Function<? super T, ? extends K> keyExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    return items.stream()
        .collect(Collectors.toMap(
            keyExtractor,
            Function.identity(),
            (existing, replacement) -> replacement, // keep last
            HashMap::new));
  }

  /** Preserves insertion order (LinkedHashMap) + keeps last on duplicate */
  public static <T, K> Map<K, T> toLookupMapOrdered(List<T> items, Function<? super T, ? extends K> keyExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    return items.stream()
        .collect(Collectors.toMap(
            keyExtractor,
            Function.identity(),
            (a, b) -> b,
            LinkedHashMap::new));
  }
}
