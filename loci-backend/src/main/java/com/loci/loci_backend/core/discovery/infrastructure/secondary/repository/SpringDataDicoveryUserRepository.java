package com.loci.loci_backend.core.discovery.infrastructure.secondary.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.UserEntityMapper;
import com.loci.loci_backend.common.user.infrastructure.secondary.repository.JpaUserRepository;
import com.loci.loci_backend.core.discovery.domain.repository.DiscoveryUserRepository;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.identity.infrastructure.secondary.specification.UserSpecifications;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataDicoveryUserRepository implements DiscoveryUserRepository {
  private final JpaUserRepository userRepository;
  private final UserEntityMapper mapper;

  @Override
  public List<UserDBId> suggestFriends(SuggestFriendCriteria criteria) {
    List<UserEntity> users = userRepository.findAll(UserSpecifications.fromCriteria(criteria));
    return mapper.toDomain(users).stream().map(User::getDbId).toList();

  }

}
