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

package com.loci.loci_backend.core.messaging.domain.aggregate;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FileSize;
import com.loci.loci_backend.core.messaging.domain.vo.Media;
import com.loci.loci_backend.core.messaging.domain.vo.MediaName;
import com.loci.loci_backend.core.messaging.domain.vo.MediaUrl;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Attachment {

  private FileContentType fileType;
  private FileSize fileSize;
  private FileName fileName;
  private Media media;

  @Builder(style = BuilderStyle.STAGED)
  public Attachment(FileContentType fileType, Media media, FileSize fileSize, FileName fileName) {
    this.fileType = fileType;
    this.media = media;
    this.fileSize = fileSize;
    this.fileName = fileName;
  }

  public static Attachment fromFile(File file) {
    return AttachmentBuilder.attachment()
        .fileType(file.getContentType())
        .media(fileToMedia(file))
        .fileSize(file.getFileSize())
        .fileName(file.getName())
        .build();

  }

  private static Media fileToMedia(File file) {
    return new Media(new MediaUrl(file.getPath().value()), new MediaName(file.getName().value()));
  }

  public MediaName getMediaName() {
    return this.media.name();

  }

  public MediaUrl getMediaUrl() {
    return this.media.url();
  }

}
