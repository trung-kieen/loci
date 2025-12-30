package com.loci.loci_backend.core.groups.infrastructure.secondary.repository;

import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaGroupRepository extends JpaRepository<GroupEntity, Long> {

  Optional<GroupEntity> findByConversationId(Long conversationId);


}
