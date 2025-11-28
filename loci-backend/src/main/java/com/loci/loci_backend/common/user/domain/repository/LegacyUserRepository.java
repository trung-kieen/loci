package com.loci.loci_backend.common.user.domain.repository;

import java.util.List;

import com.loci.loci_backend.common.user.domain.aggregate.User;

public interface LegacyUserRepository {
  List<User> fetchUsers(int limit);
}
