package com.loci.loci_backend.core.messaging.infrastructure.secondary.repository;

import java.util.List;

import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaMessageRepository extends JpaRepository<MessageEntity, Long> {

  @Query("""
      SELECT COUNT(m)
      FROM MessageEntity m
      WHERE m.conversationId = :conversationId
      AND (:lastReadId IS NULL OR (
          m.sentAt > (SELECT m2.sentAt FROM MessageEntity m2 WHERE m2.id = :lastReadId)
          OR (m.sentAt = (SELECT m2.sentAt FROM MessageEntity m2 WHERE m2.id = :lastReadId)
              AND m.id > :lastReadId)
      ))
      """)
  Long countUnreadForConversation(
      @Param("conversationId") Long conversationId,
      @Param("lastReadId") Long lastReadId);

  // TODO: create index on sentAt
  // List<Long> countUnreadMessageByConversationId(List<Long> conversationIds);
}
