package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaParticipantRepository extends JpaRepository<ConversationParticipantEntity, Long> {

  @Query("""
      SELECT NEW com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO(
        c.id,
        c.publicId,
        c.conversationType,
        c.lastMessageId,
        p.lastReadMessageId
      )
      FROM ConversationEntity c JOIN ConversationParticipantEntity p
      ON c.id = p.conversationId
      WHERE p.userId = :userId
      """)
  Page<UserConversationJpaVO> getUserConversation(@Param("userId") Long userId, Pageable pageable);

  boolean existsByConversationIdAndUserId(Long conversationId, Long userId);

  @Query("""
      SELECT p
      FROM ConversationParticipantEntity  p
      WHERE p.conversationId = :conversationId
      AND p.userId = :requestUserId
      """)
  Optional<ConversationParticipantEntity> getConnectedParticipant(@Param("conversationId") Long conversationId,
      @Param("requestUserId") Long requestUserId);

  long countByConversationId(Long conversationId);

  @Query("""
    SELECT p.userId
    FROM GroupEntity g
    JOIN
    ConversationParticipantEntity p
    ON g.conversationId = p.conversationId
    WHERE
      g.id = :groupId
    """)
  Set<Long> getUserIdInConversationByGroupId(@Param("groupId") Long groupId);

}
