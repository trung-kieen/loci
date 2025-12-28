package com.loci.loci_backend.common.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.common.validation.infrastructure.exception.MappingException;

import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ValueObjectTypeConverter {

  public <T extends Enum<T>> T wrap(@TargetType Class<T> clazz, T value) {
    return value;
  }

  @SuppressWarnings("unchecked")
  public <R, T extends ValueObject<R>> T wrap(@TargetType Class<T> clazz, Object value) {
    if (value == null) {
      return null;
    }

  /*
   * Handle ambiguous mapping raise when T = R
   * For instance T extends ValueObject<T>
   */
    if (clazz.isInstance(value)) {
      return (T) value;
    }
    R rawValue = (R) value;
    Class<?> rawType = getRawType(clazz);
    if (rawType != null && rawType.isInstance(value)) {
      return (T) NullSafe.constructOrNull(clazz, rawValue);
    } else {
      throw new MappingException();
    }

  }

  private Class<?> getRawType(Class<?> clazz) {
    for (Type genericInterface : clazz.getGenericInterfaces()) {
      if (genericInterface instanceof ParameterizedType pt && pt.getRawType().equals(ValueObject.class)) {
        Type typeArg = pt.getActualTypeArguments()[0];
        if (typeArg instanceof Class<?> c) {
          return c;
        }
      }
    }
    return null;
  }

  public <R> R unwrap(ValueObject<R> value) {
    return NullSafe.getIfPresent(value);
  }

}
