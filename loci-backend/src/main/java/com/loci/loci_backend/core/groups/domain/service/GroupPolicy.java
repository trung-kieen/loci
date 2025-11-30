package com.loci.loci_backend.core.groups.domain.service;

public interface GroupPolicy {
  void notifyNewGroupMember();

  void notifyUserLeave();

}
