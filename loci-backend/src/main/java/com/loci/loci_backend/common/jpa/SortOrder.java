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
