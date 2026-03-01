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
