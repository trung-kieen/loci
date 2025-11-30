package com.loci.loci_backend.core.groups.domain.service;

public interface GroupMembershipService {

  void addMember();

  void removeMember();

  void assignRole();

  void leaveGroup();

  void getMemberList();

}
