package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;
import com.loci.loci_backend.core.identity.domain.repository.UserPresenceRepository;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserPresenceEntity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataUserPresenceRepository implements UserPresenceRepository {
  private final CacheUserPresenceRepository cacheUserPresenceRepository;

  @Override
  public UserPresence findByUserId(UserDBId userId) {

    // get user cache if exist of not
    Optional<UserPresenceEntity> presenceOpt = cacheUserPresenceRepository.getByUserId(userId.value());
    if (presenceOpt.isPresent()) {
      return UserPresenceEntity.toDomain(presenceOpt.get());
    }

    return UserPresenceEntity.offlinePresence(userId.value());
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
