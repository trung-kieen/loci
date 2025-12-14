package com.loci.loci_backend.core.identity.domain.repository;

import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;

public interface UserSettingsRepository {

  UserSettings save(UserSettings settings);

}
