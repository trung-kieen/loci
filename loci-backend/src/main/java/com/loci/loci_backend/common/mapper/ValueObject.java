package com.loci.loci_backend.common.mapper;

public interface ValueObject<T> {

  T value();

  // boolean isEmpty();
  //
  // default String asString() {
  // return Objects.toString(value());
  // }

  public static boolean isPresent(ValueObject<?> object) {
    if (object == null || object.value() == null) {
      return false;
    }
    return true;
  }

  public static boolean isAbsent(ValueObject<?> object) {
    return !isPresent(object);

  }

}
