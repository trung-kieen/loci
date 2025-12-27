package com.loci.loci_backend.common.wire.store.secondary;

import com.loci.loci_backend.common.store.domain.repository.ObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.local.LocalObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioObjectStorage;
import com.loci.loci_backend.common.store.infrastructure.secondary.minio.MinioProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
  //   return S3Client.builder()
  //       .endpointOverride(URI.create(minioConfig.getUrl()))
  //       // .forcePathStyle(minioConfig.getForcePathStyle())
  //       .region(Region.of(minioConfig.getRegion()))
  //       .credentialsProvider(() -> AwsBasicCredentials.create(minioConfig.getAccessKey(),
  //           minioConfig.getSecretKey()))
  //       .build();
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
    //     .bucket(minioConfig.getBucket())
    //     .build();
    //
    // boolean hasExist = minioClient.bucketExists(existsArgs);
    // if (!hasExist) {
    //   MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
    //       .bucket(minioConfig.getBucket())
    //       .build();
    //   minioClient.makeBucket(makeBucketArgs);
    // }
    return minioClient;

  }



  @Primary
  @Bean
  // @Profile("minio")
  public ObjectStorage minioObjectStorage(MinioClient client, MinioProperties config) {
    log.info("Init minio service for ObjectStorage");
    return new MinioObjectStorage(client, config);
  }

  @Bean
  public ObjectStorage localObjectStorage() {
    return new LocalObjectStorage();
  }
}
