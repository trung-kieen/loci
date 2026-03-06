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

import java.time.Instant;

public final class NotBeforeTimeException extends AssertionException {

  private NotBeforeTimeException(String field, String message) {
    super(field, message);
  }

  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_BEFORE_TIME;
  }

  public static NotBeforeTimeExceptionValueBuilder strictlyNotBefore() {
    return new NotBeforeTimeExceptionBuilder("must be strictly before");
  }

  public static NotBeforeTimeExceptionValueBuilder notBefore() {
    return new NotBeforeTimeExceptionBuilder("must be before");
  }

  public static final class NotBeforeTimeExceptionBuilder
    implements NotBeforeTimeExceptionValueBuilder, NotBeforeTimeExceptionFieldBuilder, NotBeforeTimeExceptionOtherBuilder {

    private final String hint;
    private Instant value;
    private String field;
    private Instant other;

    private NotBeforeTimeExceptionBuilder(String hint) {
      this.hint = hint;
    }

    @Override
    public NotBeforeTimeExceptionFieldBuilder value(Instant value) {
      this.value = value;

      return this;
    }

    @Override
    public NotBeforeTimeExceptionOtherBuilder field(String field) {
      this.field = field;

      return this;
    }

    @Override
    public NotBeforeTimeException other(Instant other) {
      this.other = other;

      return build();
    }

    private NotBeforeTimeException build() {
      return new NotBeforeTimeException(field, message());
    }

    private String message() {
      return "Time %s in \"%s\" %s %s but wasn't".formatted(value, field, hint, other);
    }
  }

  public interface NotBeforeTimeExceptionValueBuilder {
    NotBeforeTimeExceptionFieldBuilder value(Instant value);
  }

  public interface NotBeforeTimeExceptionFieldBuilder {
    NotBeforeTimeExceptionOtherBuilder field(String field);
  }

  public interface NotBeforeTimeExceptionOtherBuilder {
    NotBeforeTimeException other(Instant other);
  }
}
