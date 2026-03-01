package com.loci.loci_backend.common.store.infrastructure.secondary.minio;

import java.nio.file.Paths;
import java.util.UUID;

import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;

import lombok.RequiredArgsConstructor;

@SecondaryPort
@RequiredArgsConstructor
public class MinioFilePathResolver {
  private final MinioProperties propertiesContext;

  static final String SEPARATE_PATH_AND_NAME_TOKEN = "====_____====";

  public String generateUniqueName(String originalFileName) {
    return UUID.randomUUID() + SEPARATE_PATH_AND_NAME_TOKEN + originalFileName;
  }

  public String getFullPath(String objectName) {

    String s3Path = Paths.get(propertiesContext.getBucket(), objectName).toString();

    // String URIPath = Paths.get(propertiesContext.getUrlPrefix(), S3Path)
    // .toString();
    // String URLPath = URI.create(URIPath).toString();
    return s3Path;

  }

  public String extractFileName(String minioFilePath) {
    int separateLength = SEPARATE_PATH_AND_NAME_TOKEN.length();
    return minioFilePath.substring(minioFilePath.lastIndexOf(SEPARATE_PATH_AND_NAME_TOKEN) + separateLength);
  }

}
