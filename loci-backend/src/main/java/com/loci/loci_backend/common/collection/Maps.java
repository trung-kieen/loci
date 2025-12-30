package com.loci.loci_backend.common.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Maps {

  private Maps() {

  }

  /**
   * An immutable-view lookup map from a list using the provided key
   * extractor.
   */
  public static <T, K> Map<K, T> toLookupMap(Collection<T> items, Function<? super T, ? extends K> keyExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<K, T> map = new HashMap<>(items.size() * 4 / 3 + 1); // initial capacity

    for (T item : items) {
      K key = keyExtractor.apply(item);
      T previous = map.putIfAbsent(key, item);
      if (previous != null) {
        throw new IllegalStateException("Duplicate key detected: " + key);
      }
    }

    return Collections.unmodifiableMap(map);
  }

  public static <T, K, V> Map<K, V> toLookupMap(Collection<T> items, Function<? super T, ? extends K> keyExtractor, Function<? super T, ? extends V> valueExtractor) {
    if (items == null || items.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<K, V> map = new HashMap<>(items.size() * 4 / 3 + 1); // initial capacity

    for (T item : items) {
      K key = keyExtractor.apply(item);
      V  value = valueExtractor.apply(item);
      V previous = map.putIfAbsent(key, value);
      if (previous != null) {
        throw new IllegalStateException("Duplicate key detected: " + key);
      }
    }

    return Collections.unmodifiableMap(map);
  }

  // Same as above but uses stream but slower
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

  // Keeps the last occurrence on duplicate keys instead of throwing
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
