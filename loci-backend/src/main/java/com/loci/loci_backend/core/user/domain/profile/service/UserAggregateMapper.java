package com.loci.loci_backend.core.user.domain.profile.service;

import com.loci.loci_backend.common.user.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfileChanges;

public interface UserAggregateMapper {
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile);
  public KeycloakUser toKeycloakUser(User user);
}
