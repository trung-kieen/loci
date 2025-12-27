package com.loci.loci_backend.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.loci.loci_backend.common.mapper.ValueObject;

import lombok.extern.log4j.Log4j2;

/**
 * null-safe population of a builder.
 */
@Log4j2
public final class NullSafe {

  private NullSafe() {
  }

  /**
   * Apply function consumer if the value from the supplier function is not null
   */
  public static <T> void applyIfPresent(Supplier<T> supplier, Consumer<T> consumer) {
    Optional.ofNullable(supplier.get()).ifPresent(consumer);
  }

  /**
   * Apply supplier function supplier(T)
   * if ressult if supplier is not null then execute the runnable action
   */
  public static <T> void applyIfNull(Supplier<T> supplier, Runnable action) {
    if (supplier.get() == null) {
      action.run();
      ;
    }

  }

  /**
   * Apply function fn if the value is not null
   */
  public static <T, R> R getIfPresent(T value, Function<T, R> fn) {
    return value != null ? fn.apply(value) : null;
  }

  /**
   * If the value object is not null get field .value()
   * Unwrap VO function
   */
  public static <R> R getIfPresent(ValueObject<R> value) {
    return getIfPresent(value, (v) -> v.value());
  }

  /**
   * Replace for ternary
   * If the value is null return the defaultValue
   */
  public static <T> T getOrDefault(T value, T defaultValue) {
    return value != null ? value : defaultValue;
  }

  /**
   * Apply the Value constructor if value is not null else return null value
   * Only apply for class if interface ValueObject
   * @see NullSafeTest for more details
   */
  public static <R, T extends ValueObject<R>> T constructOrNull(Class<T> c, R value) {
    if (c == null) {
      throw new IllegalArgumentException("class to construct cannot be null");
    }
    if (value == null) {
      return null;
    }
    Class<T> clazz = c; // CORRECT - use the Class object directly
    // Class<?> clazz = c.getClass();
    Class<?> paramType = value.getClass();
    try {
      // Create matching constructor with param type of value
      Constructor<?> constructor = clazz.getConstructor(paramType);

      @SuppressWarnings("unchecked")
      T instance = (T) constructor.newInstance(value);

      return instance;
    } catch (NoSuchMethodException e) {
      log.error("No constructor found for {} with parameter type {}. " +
          "Available constructors: {}",
          clazz.getName(), paramType.getName(),
          clazz.getConstructors(), e);

    } catch (InstantiationException e) {
      log.error("Failed to instantiate {}. Class might be abstract or interface.",
          clazz.getName(), e);

    } catch (IllegalAccessException e) {
      log.error("Constructor for {} is not accessible. Check visibility modifiers.",
          clazz.getName(), e);

    } catch (InvocationTargetException e) {
      log.error("Constructor threw an exception: {}",
          e.getCause().getMessage(), e.getCause());

    } catch (SecurityException e) {
      log.error("Security manager denied access to constructor for {}",
          clazz.getName(), e);

    } catch (ClassCastException e) {
      log.error("Created instance cannot be cast to target type {}",
          clazz.getName(), e);
    }

    return null;
  }
  public static boolean isPresent(ValueObject<?> object){
    if (object == null || object.value() == null){
      return false;
    }
    return true;
  }

}
