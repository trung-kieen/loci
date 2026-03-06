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

package com.loci.loci_backend.common.wire.store.secondary;

import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.local.LocalObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioFilePathResolver;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
public class StoreConfiguration {

  private final MinioProperties minioConfig;

  // @Bean
  // // @Profile("s3")
  // public S3Client s3Client() {
  // return S3Client.builder()
  // .endpointOverride(URI.create(minioConfig.getUrl()))
  // // .forcePathStyle(minioConfig.getForcePathStyle())
  // .region(Region.of(minioConfig.getRegion()))
  // .credentialsProvider(() ->
  // AwsBasicCredentials.create(minioConfig.getAccessKey(),
  // minioConfig.getSecretKey()))
  // .build();
  // }

  @SneakyThrows
  @Bean
  // @Profile("minio")
  public MinioClient minioClient() {
    MinioClient minioClient;
    minioClient = MinioClient.builder()
        .endpoint(minioConfig.getUrl())
        .region(minioConfig.getRegion())
        .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
        .build();

    // Create bucket via docker mc service

    // // Create bucket if not exist
    // BucketExistsArgs existsArgs = BucketExistsArgs.builder()
    // .bucket(minioConfig.getBucket())
    // .build();
    //
    // boolean hasExist = minioClient.bucketExists(existsArgs);
    // if (!hasExist) {
    // MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
    // .bucket(minioConfig.getBucket())
    // .build();
    // minioClient.makeBucket(makeBucketArgs);
    // }
    return minioClient;

  }

  @Bean
  @ConditionalOnProperty(name = "upload.minio.enable", havingValue = "true")
  public ObjectStorage minioObjectStorage(MinioClient client, MinioProperties config, MinioFilePathResolver filePathResolver) {
    log.info("Minio Object Storage on {}", config.getUrl());
    return new MinioObjectStorage(client, config, filePathResolver);
  }

  @ConditionalOnMissingBean(ObjectStorage.class)
  @Bean
  public ObjectStorage localObjectStorage() {
    log.info("Object Storage fallback use local storage");
    return new LocalObjectStorage();
  }
}
