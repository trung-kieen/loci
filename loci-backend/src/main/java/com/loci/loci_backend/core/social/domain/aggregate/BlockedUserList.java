package com.loci.loci_backend.core.social.domain.aggregate;

import com.loci.loci_backend.common.user.domain.aggregate.User;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class BlockedUserList {
  private Page<User> users;

  public BlockedUserList(Page<User> users) {
    this.users = users;
  }

}
