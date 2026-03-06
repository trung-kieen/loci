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

package com.loci.loci_backend.common.store.infrastructure.primary.vo;

// import java.nio.file.Paths;
// import java.util.UUID;

// import com.loci.loci_backend.common.store.domain.vo.FileName;
// import com.loci.loci_backend.common.store.domain.vo.FilePath;
// import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioProperties;

public record MinioPath(String value) {

  // static final String SEPARATE_PATH_AND_NAME_TOKEN = "====_____====";

  // public FilePath fullPath(MinioProperties propertiesContext) {

  //   String S3Path = Paths.get(propertiesContext.getBucket(), this.value()).toString();

  //   // String URIPath = Paths.get(propertiesContext.getUrlPrefix(), S3Path)
  //   // .toString();
  //   // String URLPath = URI.create(URIPath).toString();
  //   return new FilePath(S3Path);
  // }



  // public static MinioPath generateUniquePath(FileName fileName) {
  //   return new MinioPath(UUID.randomUUID() + SEPARATE_PATH_AND_NAME_TOKEN + fileName.value());
  // }


  // public static FileName extractFileName(MinioPath path) {
  //   int separateLength = SEPARATE_PATH_AND_NAME_TOKEN.length();
  //   String filePath = path.value();
  //   return new FileName(filePath.substring(filePath.lastIndexOf(SEPARATE_PATH_AND_NAME_TOKEN) + separateLength));
  // }

}
