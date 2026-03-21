/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.conversation.infrastructure.secondary.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.UserConversationJpaVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Query("""
        SELECT p.userId
        FROM ConversationParticipantEntity p
        WHERE p.conversationId = :conversationId
      """)
  List<Long> getUserIdInConversation(@Param("conversationId") Long conversationId);


  @Query("""
    SELECT DISTINCT(p) FROM ConversationParticipantEntity p
    WHERE p.conversationId in (:conversationIds)
  """)
  List<ConversationParticipantEntity> findAllByConversationIdIn(@Param("conversationIds") Set<Long> conversationIds);

  @Query("""
      SELECT p
      FROM ConversationParticipantEntity  p
      WHERE p.conversationId = :conversationId
      AND p.userId <> :requestUserId
      """)
  Optional<ConversationParticipantEntity> getTargetParticipantInDirectConversation(@Param("conversationId") Long conversationId,
      @Param("requestUserId") Long requestUserId);

  List<ConversationParticipantEntity> findAllByConversationId(Long conversationId);

  @Modifying
  @Query("""
    UPDATE ConversationParticipantEntity p SET p.lastReadMessageId = :messageId
    WHERE p.id = :participantId
  """)
  public void markLatestReadMessage(@Param("participantId") Long participantId, @Param("messageId") Long messageId);

  List<ConversationParticipantEntity> findAllByUserId(Long userId);

}
