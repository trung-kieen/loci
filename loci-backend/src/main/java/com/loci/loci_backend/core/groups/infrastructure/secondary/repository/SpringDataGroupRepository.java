package com.loci.loci_backend.core.groups.infrastructure.secondary.repository;

import java.util.Optional;

import com.loci.loci_backend.core.conversation.domain.vo.ConversationId;
import com.loci.loci_backend.core.groups.domain.aggregate.GroupProfile;
import com.loci.loci_backend.core.groups.domain.aggregate.CreateGroupProfileRequest;
import com.loci.loci_backend.core.groups.domain.repository.GroupRepository;
import com.loci.loci_backend.core.groups.infrastructure.secondary.entity.GroupEntity;
import com.loci.loci_backend.core.groups.infrastructure.secondary.mapper.GroupEntityMapper;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataGroupRepository implements GroupRepository {

  private final JpaGroupRepository repository;
  private final GroupEntityMapper mapper;

  @Override
  public Optional<GroupProfile> getByConversationId(ConversationId conversationId) {
    return repository.findByConversationId(conversationId.value()).map(mapper::toDomain);
  }

  @Override
  public GroupProfile createProfile(CreateGroupProfileRequest request) {
    GroupEntity entity = mapper.from(request);
    GroupEntity savedProfile = repository.save(entity);
    return mapper.toDomain(savedProfile);
  }

  @Override
  public GroupProfile findByConversationId(ConversationId conversationId) {
    return this.getByConversationId(conversationId).orElseThrow(EntityNotFoundException::new);
  }


}
