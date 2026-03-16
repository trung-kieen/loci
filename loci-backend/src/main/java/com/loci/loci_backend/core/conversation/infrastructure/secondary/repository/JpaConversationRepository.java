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
import java.util.UUID;

import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationEntity;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaConversationRepository extends JpaRepository<ConversationEntity, Long> {

  @Query("""
      SELECT c
      FROM ConversationEntity c
      JOIN ConversationParticipantEntity p1 ON p1.conversationId = c.id
      JOIN ConversationParticipantEntity p2 ON p2.conversationId = c.id
      WHERE
      c.deleted = false
      AND p1.userId = :a
      AND p2.userId = :b
      AND p1.userId <> p2.userId
      AND (c.creatorId = :a OR c.creatorId = :b)
      AND c.conversationType = 'ONE_TO_ONE'

      """)

  Optional<ConversationEntity> getConversationBetweenUser(@Param("a") Long a, @Param("b") Long b);

  @Query("""
      SELECT COUNT(c) > 0
      FROM ConversationEntity c
      WHERE c.id = :conversationId AND conversationType = 'GROUP'

      """)
  boolean existsGroupConversation(@Param("conversationId") Long conversationId);

  @Query("""
      SELECT NEW com.loci.loci_backend.core.conversation.infrastructure.secondary.vo.GroupConversationMetadataJpaVO(
        c.id,
        c.publicId,
        g.id,
        g.publicId,
        g.groupName,
        g.groupProfilePicture,
        COUNT(p)
      )
      FROM GroupEntity g
      JOIN ConversationEntity c
      ON g.conversationId = c.id
      JOIN ConversationParticipantEntity p
      ON p.conversationId = c.id
      WHERE c.id IN (:conversationIds)
      GROUP BY c.id, c.publicId, g.id, g.publicId, g.groupName, g.groupProfilePicture

      """)
  List<GroupConversationMetadataJpaVO> getGroupMetadataByConversationIds(
      @Param("conversationIds") Set<Long> conversationIds);

  Optional<ConversationEntity> findByPublicId(UUID publicId);

  @Modifying
  @Query("UPDATE ConversationEntity c SET c.lastMessageId = :messageId WHERE c.id = :conversationId")
  void markAsLatestMessage(@Param("conversationId") Long conversationId,@Param("messageId") Long messageId);

}
