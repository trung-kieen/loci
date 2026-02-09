package com.loci.loci_backend.core.messaging.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  Optional<MessageEntity> findByPublicId(UUID publicId);

  @Query(value = """
      SELECT * FROM message m WHERE m.conversation_id = :conversationId
      ORDER BY sent_at DESC
      LIMIT :limit
      """, nativeQuery = true)
  List<MessageEntity> findLatestByConversationId(@Param("conversationId") Long conversationId,
      @Param("limit") Integer pageLimit);

  @Query("""
      SELECT m FROM MessageEntity m
      WHERE m.conversationId = :conversationId
        AND m.sentAt < (SELECT m2.sentAt FROM MessageEntity  m2 WHERE m2.id = :beforeId)
      ORDER BY m.sentAt DESC
      """)
  Page<MessageEntity> findOlderMessagesByConversationId(@Param("conversationId") Long conversationId,
      @Param("beforeId") Long beforeMessageId, Pageable pageable);
}
