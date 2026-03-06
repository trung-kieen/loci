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

package com.loci.loci_backend.common.store.infrastructure.primary.mapper;

import java.io.IOException;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.aggregate.FileBuilder;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.domain.vo.FileSize;

import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@PrimaryMapper
@RequiredArgsConstructor
public class RestFileMapper {
  public File toDomain(MultipartFile file) throws IOException {

    return FileBuilder.file()
        .path(new FilePath(file.getOriginalFilename()))
        .stream(new FileInputStream(file.getInputStream()))
        .contentType(new FileContentType(file.getContentType()))
        .name(new FileName(file.getOriginalFilename()))
        .fileSize(new FileSize(file.getSize()))
        .build();

  }

}
