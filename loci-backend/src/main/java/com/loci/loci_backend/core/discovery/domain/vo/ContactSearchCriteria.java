package com.loci.loci_backend.core.discovery.domain.vo;

public class ContactSearchCriteria {
  private final String keyword;

  public ContactSearchCriteria(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

}
