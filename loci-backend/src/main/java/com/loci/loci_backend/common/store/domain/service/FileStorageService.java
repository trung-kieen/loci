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

package com.loci.loci_backend.common.store.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FilePath;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FileStorageService {
  private final ObjectStorage objectStorage;

  // public File saveFile(FilePath filePath, FileInputStream file, FileContentType contentType, FileName fileName,
  //     FileSize fileSize) {
  //   return objectStorage.saveObject(filePath, file, contentType, fileName, fileSize);
  // }

  public File saveFile(File file, FilePath assignFilePath) {
    return objectStorage.saveObject(assignFilePath, file.getStream(), file.getContentType(), file.getName(),
        file.getFileSize());
  }

  public File saveFile(File file) {
    return objectStorage.saveObject(file.getPath(), file.getStream(), file.getContentType(), file.getName(),
        file.getFileSize());
  }

  public void deleteFile(FilePath filePath) {
    objectStorage.deleteObject(filePath);
  }

  public File getFile(FilePath filePath) {
    return objectStorage.getObject(filePath);
  }

  public FileInputStream getFileStream(FilePath filePath) {
    return objectStorage.getObject(filePath).getStream();
  }

}
