package com.loci.loci_backend.core.identity.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.aggregate.UserPresence;

public interface UserPresenceRepository {

  UserPresence findByUserId(UserDBId userId);

  List<UserPresence> findAllByUserIds(Collection<UserDBId> ids);

  Map<UserDBId, UserPresence> lookupPresencesByUserIds(Collection<UserDBId> userIds);

}
