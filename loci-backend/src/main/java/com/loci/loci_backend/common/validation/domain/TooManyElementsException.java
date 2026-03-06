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

package com.loci.loci_backend.common.validation.domain;

public class TooManyElementsException extends AssertionException {
  private final String field;
  private final String currentSize;
  private final String maxSize;

  public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
    super(builder.field, builder.message());
    field = builder.field;
    maxSize = String.valueOf(builder.maxSize);
    currentSize = String.valueOf(builder.size);
  }

  /*
   * Use static inner class to perform builder exception from method base
   * instead of constructor overloadding
   */
  public static class TooManyElementsExceptionBuilder {
    String field;
    int maxSize;
    int size;

    public TooManyElementsExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public TooManyElementsExceptionBuilder maxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public TooManyElementsExceptionBuilder size(int size) {
      this.size = size;
      return this;
    }

    public TooManyElementsException build() {
      return new TooManyElementsException(this);
    }

    public String message() {
      return new StringBuilder()
          .append("Size of collection \"")
          .append(field)
          .append("\" must at most ")
          .append(maxSize)
          .append(" but was ")
          .append(size)
          .toString();
    }

  }
  public static TooManyElementsExceptionBuilder builder(){
    return new TooManyElementsExceptionBuilder();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.TOO_MANY_ELEMENTS;
  }

}
