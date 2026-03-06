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
