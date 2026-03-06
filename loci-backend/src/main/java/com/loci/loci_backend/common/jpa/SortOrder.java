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

package com.loci.loci_backend.common.jpa;

public record SortOrder(String field, String columnName, OrderDirection order) {

  // Can replace with enum
  public static SortOrder byLastUpdateDesc() {
    return new SortOrder("lastModifiedDate", "last_modified_date", OrderDirection.DESC);
  }

  public static SortOrder byField(String field, String columnName, OrderDirection direction) {
    return new SortOrder(field, columnName, direction);
  }

}
