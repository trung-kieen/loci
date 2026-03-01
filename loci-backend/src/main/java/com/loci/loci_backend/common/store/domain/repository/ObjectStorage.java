package com.loci.loci_backend.common.store.domain.repository;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.domain.vo.FileSize;

public interface ObjectStorage {

  public File saveObject(FilePath path, FileInputStream file, FileContentType contentType, FileName fileName,
      FileSize fileSize) ;

  void deleteObject(FilePath filePath);

  File getObject(FilePath filePath);

}
