package com.loci.loci_backend.core.groups.infrastructure.secondary.repository;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.core.groups.domain.repository.GroupIdTranslator;
import com.loci.loci_backend.core.groups.domain.vo.GroupId;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpringDataGroupIdTranslator implements GroupIdTranslator {@Override
  public GroupId toInternal(PublicId publicId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toInternal'");
  }

  @Override
  public PublicId toPublic(GroupId internalId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toPublic'");
  }

}
