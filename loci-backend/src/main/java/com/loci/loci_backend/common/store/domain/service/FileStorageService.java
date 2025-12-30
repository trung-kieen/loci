package com.loci.loci_backend.common.store.domain.service;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService;
import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FilePath;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FileStorageService {
  private final ObjectStorage objectStorage;

  public File saveFile(FilePath filePath, FileInputStream file, FileContentType contentType) {
    return objectStorage.saveObject(filePath, file, contentType);
  }

  public File saveFile(File file, FilePath assignFilePath) {
    return objectStorage.saveObject(assignFilePath, file.stream(), file.contentType());
  }

  public File saveFile(File file) {
    return objectStorage.saveObject(file.path(), file.stream(), file.contentType());
  }

  public void deleteFile(FilePath filePath) {
    objectStorage.deleteObject(filePath);
  }

  public File getFile(FilePath filePath) {
    return objectStorage.getObject(filePath);
  }

  public FileInputStream getFileStream(FilePath filePath) {
    return objectStorage.getObject(filePath).stream();
  }

}
