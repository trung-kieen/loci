/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.common.ddd.infrastructure.mapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.common.validation.infrastructure.exception.MappingException;

import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

/**
 * Provide mapstruct type translator from ValueObject to infrastructure type
 * Java not support to create custom autoboxing so any translate from domain to
 * infrastructure require a explicit layer
 *
 * @see ValueObject
 */
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

  public <R> R unwrap(ValueObject<R> value) {
    return NullSafe.getIfPresent(value);
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

}
