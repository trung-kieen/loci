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
