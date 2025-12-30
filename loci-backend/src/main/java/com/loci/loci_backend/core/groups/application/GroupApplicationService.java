package com.loci.loci_backend.core.groups.application;

import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.service.GroupManager;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class GroupApplicationService {

  private final GroupManager groupManager;

  public GroupProfile createGroupProfile(CreateGroupProfileRequest  createProfileRequest) {
    return groupManager.createGroupProfile(createProfileRequest);
  }



}
