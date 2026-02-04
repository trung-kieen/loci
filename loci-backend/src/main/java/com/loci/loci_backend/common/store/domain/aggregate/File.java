package com.loci.loci_backend.common.store.domain.aggregate;

import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FilePath;

import org.jilt.Builder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class File {
  private FilePath path;
  private FileInputStream stream;

  private FileContentType contentType;
  @Builder
  public File(FilePath path, FileInputStream stream, FileContentType contentType) {
    this.path = path;
    this.stream = stream;
    this.contentType = contentType;
  }

}
