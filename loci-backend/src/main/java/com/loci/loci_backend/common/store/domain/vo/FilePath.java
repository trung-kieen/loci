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

package com.loci.loci_backend.common.store.domain.vo;

// import java.util.UUID;

// import com.loci.loci_backend.common.store.domain.aggregate.File;

public record FilePath(String value) {
  // static final String SEPARATE_PATH_AND_NAME_TOKEN = "====_____====";

  // public static FilePath generateUniquePath(File file) {
  //   return new FilePath(UUID.randomUUID() + SEPARATE_PATH_AND_NAME_TOKEN + file.getPath().value());
  // }

  // public static FileName extractFileName(FilePath path) {
  //   int separateLength = SEPARATE_PATH_AND_NAME_TOKEN.length();
  //   String filePath = path.value();
  //   return new FileName(filePath.substring(filePath.lastIndexOf(SEPARATE_PATH_AND_NAME_TOKEN) + separateLength));
  // }

}
