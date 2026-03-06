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

package com.loci.loci_backend.common.store.domain.aggregate;

import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.domain.vo.FileSize;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class File {
  private FilePath path;
  private FileInputStream stream;

  private FileContentType contentType;
  private FileName name;
  private FileSize fileSize;

  @Builder(style = BuilderStyle.STAGED)
  public File(FilePath path, FileInputStream stream, FileContentType contentType, FileName name, FileSize fileSize) {
    this.path = path;
    this.stream = stream;
    this.contentType = contentType;
    this.name = name;
    this.fileSize = fileSize;
  }

}
