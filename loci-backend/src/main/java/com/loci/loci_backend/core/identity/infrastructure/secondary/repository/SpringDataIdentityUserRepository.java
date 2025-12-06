package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.domain.repository.IdentityUserRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.IdentityEntityMapper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SpringDataIdentityUserRepository implements IdentityUserRepository{
  private final JpaUserRepository repository;
  private final IdentityEntityMapper identityEntityMapper;

  @Override
  public List<UserSummary> getUserSummary(List<UserDBId> ids) {
    List<Long> userIds = ids.stream().map(UserDBId::value).toList();
    List<UserEntity> userEntities = repository.findAllById(userIds);

    List<UserSummary> userSummaries = identityEntityMapper.toUserSummary(userEntities);

    return userSummaries;
  }

}
