package com.loci.loci_backend.core.user.domain.profile.vo;

public class UserSearchCriteria {
  private final String keyword;

  public UserSearchCriteria(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

}
