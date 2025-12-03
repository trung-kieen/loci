package com.loci.loci_backend.common.util;

import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ValueObjectTypeConverter {

  public <R, T extends ValueObject<R>> ValueObject<R> wrap(@TargetType Class<T> clazz, R value) {
    return NullSafe.constructOrNull(clazz, value);
  }

  public <R> R unwrap(ValueObject<R> value) {
    return NullSafe.getIfPresent(value);
  }



}
