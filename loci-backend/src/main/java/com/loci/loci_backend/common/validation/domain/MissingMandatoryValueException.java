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

public class MissingMandatoryValueException extends AssertionException {

  private MissingMandatoryValueException(String field, String reason){
    super(field, reason);
  }

  public static MissingMandatoryValueException forBlankValue(String field){
    return new MissingMandatoryValueException(field, defaultMessage(field, "blank"));
  }
  public static MissingMandatoryValueException forNullValue(String field){
    return new MissingMandatoryValueException(field, defaultMessage(field, "null"));
  }


  public static MissingMandatoryValueException forEmptyValue(String field){
    return new MissingMandatoryValueException(field, defaultMessage(field, "empty"));
  }


  private static String defaultMessage(String field, String reason){
    return new StringBuilder()
    .append("The field \"")
    .append(field)
    .append("\" is mandatory and wasn't set")
    .append("(")
    .append(reason)
    .append(")")
    .toString()
    ;

  }
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.MISSING_MANDATORY_VALUE;
  }


}
