package com.loci.loci_backend.core.user.profile.domain.vo;

public class UserSearchCriteria {
  private final String keyword;

  public UserSearchCriteria(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

}
