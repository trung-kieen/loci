package com.loci.loci_backend.common.store.infrastructure.secondary.local;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.domain.vo.FileSize;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalObjectStorage implements ObjectStorage {

  @Override
  public File saveObject(FilePath path, FileInputStream file, FileContentType contentType, FileName fileName,
      FileSize fileSize) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'saveObject'");
  }

  @Override
  public void deleteObject(FilePath filePath) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteObject'");
  }

  @Override
  public File getObject(FilePath filePath) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getObject'");
  }

}
