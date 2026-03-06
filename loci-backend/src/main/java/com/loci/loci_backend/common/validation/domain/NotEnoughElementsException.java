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

public class NotEnoughElementsException extends AssertionException {

  private final String field;
  private final String currentSize;
  private final String minSize;

  public NotEnoughElementsException(NotEnoughElementsExceptionBuilder builder) {
    super(builder.field, builder.message());
    this.field = builder.field;
    this.minSize = String.valueOf(builder.minSize);
    this.currentSize = String.valueOf(builder.currentSize);
  }

  public static class NotEnoughElementsExceptionBuilder {
    private String field;
    private int currentSize;
    private int minSize;

    public NotEnoughElementsExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public NotEnoughElementsExceptionBuilder currentSize(int currentSize) {
      this.currentSize = currentSize;
      return this;
    }

    public NotEnoughElementsExceptionBuilder minSize(int minSize) {
      this.minSize = minSize;
      return this;
    }

    public NotEnoughElementsException build() {
      return new NotEnoughElementsException(this);
    }

    public String message() {
      return new StringBuilder().append("Size of collection \"")
          .append(field)
          .append("\" must at least ")
          .append(minSize)
          .append(" but was ")
          .append(currentSize)
          .toString();
    }

  }

  public static NotEnoughElementsExceptionBuilder builder() {
    return new NotEnoughElementsExceptionBuilder();
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_ENOUGH_ELEMENTS;
  }
}
