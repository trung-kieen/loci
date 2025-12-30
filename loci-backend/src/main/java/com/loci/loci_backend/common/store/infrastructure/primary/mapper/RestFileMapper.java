package com.loci.loci_backend.common.store.infrastructure.primary.mapper;

import java.io.IOException;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.aggregate.FileBuilder;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FilePath;

import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestFileMapper {
  public File toDomain(MultipartFile file) throws IOException {

    return FileBuilder.file()
        .stream(new FileInputStream(file.getInputStream()))
        .contentType(new FileContentType(file.getContentType()))
        .path(new FilePath(file.getOriginalFilename()))
        .build();

  }

}
