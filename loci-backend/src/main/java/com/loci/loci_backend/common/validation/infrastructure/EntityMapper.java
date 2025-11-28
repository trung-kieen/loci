package com.loci.loci_backend.common.validation.infrastructure;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * null-safe population of a builder.
 */
public final class EntityMapper {

  private EntityMapper() {
  }

  public static <T> void applyIfPresent(Supplier<T> supplier, Consumer<T> consumer) {
    Optional.ofNullable(supplier.get()).ifPresent(consumer);
  }

  public static <T> void applyIfNull(Supplier<T> supplier, Runnable action) {
    if (supplier.get() == null) {
      action.run();
      ;
    }

  }

  public static <T, R> R getIfPresent(T value, Function<T, R> fn) {
    return value != null ? fn.apply(value) : null;
  }



  public static <T> T getOrDefault(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }

}
