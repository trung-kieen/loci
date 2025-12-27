package com.loci.loci_backend.common.jpa;

import lombok.Getter;

@Getter
public enum OrderDirection {
  ASC("asc"), DESC("desc");

  private String value;

  private OrderDirection(String value) {
    this.value = value;
  }

}
