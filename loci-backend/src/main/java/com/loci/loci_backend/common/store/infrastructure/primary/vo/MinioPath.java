package com.loci.loci_backend.common.store.infrastructure.primary.vo;

import java.nio.file.Paths;

import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioProperties;

public record MinioPath(String value) {
  public FilePath fullPath(MinioProperties propertiesContext) {

    String S3Path = Paths.get(propertiesContext.getBucket(), this.value()).toString();

    // String URIPath = Paths.get(propertiesContext.getUrlPrefix(), S3Path)
    //     .toString();
    // String URLPath = URI.create(URIPath).toString();
    return new FilePath(S3Path);

  }
}
