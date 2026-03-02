package com.loci.loci_backend.core.social.infrastructure.primary.mapper;

import com.loci.loci_backend.common.collection.Pages;
import com.loci.loci_backend.common.ddd.infrastructure.contract.Domain2RestMapper;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.social.domain.aggregate.BlockedUserList;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUser;
import com.loci.loci_backend.core.social.infrastructure.primary.payload.RestBlockedUserList;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestBlockedUserMapper implements Domain2RestMapper<User, RestBlockedUser> {
  private final MapStructRestBlockedUserMapper mapstruct;

  @Override
  public RestBlockedUser from(User domain) {
    return mapstruct.from(domain);
  }

  public RestBlockedUserList from(BlockedUserList blockedUserList) {
    var rest = Pages.map(blockedUserList.getUsers(), this::from);
    return new RestBlockedUserList(rest);
  }

}
