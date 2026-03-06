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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

// @Profile("minio")
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "upload.minio")
public class MinioProperties {
  // Endpoint URL for minio host, set empty for S3)
  private String scheme = "http";
  private String url;
  private String accessKey;
  private String secretKey;

  // Https required
  private boolean secure = false;

  // S3 use for global unique identify
  private String bucket;
  private String metricName;


  // Required protocol if use minio self host
  private String urlPrefix;
  private String region = "us-east-1";

  // True for MinIO;
  private boolean forcePathStyle = false;
  private boolean enable;
}
