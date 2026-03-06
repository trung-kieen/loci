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

package com.loci.loci_backend.common.store.infrastructure.secondary.minio;

import java.io.InputStream;

import com.loci.loci_backend.common.store.domain.aggregate.File;
import com.loci.loci_backend.common.store.domain.aggregate.FileBuilder;
import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.domain.vo.FileContentType;
import com.loci.loci_backend.common.store.domain.vo.FileInputStream;
import com.loci.loci_backend.common.store.domain.vo.FileName;
import com.loci.loci_backend.common.store.domain.vo.FilePath;
import com.loci.loci_backend.common.store.domain.vo.FileSize;
import com.loci.loci_backend.common.store.infrastructure.primary.vo.MinioPath;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MinioObjectStorage implements ObjectStorage {

  private final MinioClient minioClient;
  private final MinioProperties configurationProperties;
  private final MinioFilePathResolver filePathResolver;

  public MinioObjectStorage(MinioClient client, MinioProperties config, MinioFilePathResolver filePathResolver) {
    super();
    this.minioClient = client;
    this.configurationProperties = config;
    this.filePathResolver = filePathResolver;
  }

  @SneakyThrows
  @Override
  public File saveObject(FilePath path, FileInputStream file, FileContentType contentType, FileName fileName,
      FileSize fileSize) {
    InputStream inputStream = file.value();
    // MinioPath uploadPath = new MinioPath(path.value());
    // MinioPath uploadPath = MinioPath.generateUniquePath(fileName);

    // use to save in minio
    MinioPath minioObjectName = new MinioPath(filePathResolver.generateUniqueName(fileName.value()));
    log.info("Designate image path for upload path {}", minioObjectName);
    PutObjectArgs args = PutObjectArgs.builder()
        .bucket(configurationProperties.getBucket())
        .object(minioObjectName.value())
        .stream(inputStream, inputStream.available(), -1)
        // .headers(headers)
        .contentType(contentType.value())
        .build();
    ObjectWriteResponse response = minioClient.putObject(args);
    log.info("Minio put response:{}", response);
    // MinioPath minioPath =

    FilePath objectFullPath = new FilePath(filePathResolver.getFullPath(minioObjectName.value()));
    // FilePath fullPath = uploadPath.fullPath(configurationProperties);
    log.debug("File success upload to {}", objectFullPath);
    return FileBuilder.file()
        .path(objectFullPath)
        .stream(new FileInputStream(inputStream))
        .contentType(contentType)
        .name(fileName)
        .fileSize(fileSize)
        .build();
  }

  @SneakyThrows
  @Override
  public void deleteObject(FilePath path) {
    String filePath = path.value();
    minioClient.removeObject(RemoveObjectArgs
        .builder()
        .bucket(configurationProperties.getBucket())
        .object(filePath)
        .build());
  }

  @SneakyThrows
  @Override
  public File getObject(FilePath path) {
    MinioPath minioPath = new MinioPath(path.value());

    StatObjectResponse stat = minioClient.statObject(
        StatObjectArgs.builder()
            .bucket(configurationProperties.getBucket())
            .object(minioPath.value())
            .build());

    FileName fileName = new FileName(filePathResolver.extractFileName(minioPath.value()));
    // FileName fileName = MinioPath.extractFileName(minioPath);
    FileInputStream stream = new FileInputStream(minioClient.getObject(
        GetObjectArgs.builder()
            .bucket(configurationProperties.getBucket())
            .object(minioPath.value())
            .build()));
    // Return with with prefix is minio self host with fix or s3 global unique
    // FilePath fullPath = minioPath.fullPath(configurationProperties);
    FilePath fullPath = new FilePath(filePathResolver.getFullPath(minioPath.value()));
    return FileBuilder.file()
        .path(fullPath)
        .stream(stream)
        .contentType(new FileContentType(stat.contentType()))
        .name(fileName)
        .fileSize(new FileSize(stat.size()))
        .build();
  }

}
