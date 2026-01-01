package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.mapper.UserPresenceEntityMapper;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class SpringDataUserPresenceRepository implements UserPresenceRepository {
  private final CacheUserPresenceRepository cacheUserPresenceRepository;
  private final UserPresenceEntityMapper mapper;

  @Override
  public UserPresence findByUserId(UserDBId userId) {

    // get user cache if exist of not
    Optional<UserPresenceEntity> presenceOpt = cacheUserPresenceRepository.getByUserId(userId.value());
    if (presenceOpt.isPresent()) {
      return mapper.toDomain(presenceOpt.get());
    }

    return mapper.toDomain(UserPresenceEntity.offline(userId.value()));
  }

  @Override
  public List<UserPresence> findAllByUserIds(Collection<UserDBId> ids) {
    return ids.stream().map(this::findByUserId).toList();
  }

  @Override
  public Map<UserDBId, UserPresence> lookupPresencesByUserIds(Collection<UserDBId> userIds) {
    return userIds.stream().collect(Collectors.toMap(Function.identity(), this::findByUserId));
  }

}
