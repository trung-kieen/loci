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

import java.util.Map;

public final class NumberValueTooHighException extends AssertionException {

  private final String max;
  private final String value;

  private NumberValueTooHighException(NumberValueTooHighExceptionBuilder builder) {
    super(builder.field, builder.message());
    max = builder.maxValue;
    value = builder.value;
  }

  public static NumberValueTooHighExceptionBuilder builder() {
    return new NumberValueTooHighExceptionBuilder();
  }

  public static class NumberValueTooHighExceptionBuilder {

    private String field;
    private String maxValue;
    private String value;

    public NumberValueTooHighExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public NumberValueTooHighExceptionBuilder maxValue(String maxValue) {
      this.maxValue = maxValue;

      return this;
    }

    public NumberValueTooHighExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    public NumberValueTooHighException build() {
      return new NumberValueTooHighException(this);
    }
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NUMBER_VALUE_TOO_HIGH;
  }

  @Override
  public Map<String, String> parameters() {
    return Map.of("max", max, "value", value);
  }
}
