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

package com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
  private String authServerUrl;
  // for fetching certs
  private String internalServerUrl;
  private String realm;
  private String resource;
  private boolean publicClient;
  private Credentials credentials = new Credentials();

  // fields for admin access
  // NOTE: Mandatory via system variable
  private String adminRealm = "master";
  private String adminUsername;
  private String adminPassword;
  private String migrationPassword;

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:#{null}}")
  private String issuerUri;

  @PostConstruct
  public void init() {
    if ((authServerUrl == null || realm == null) && issuerUri != null) {
      // Parse issuer-uri to extract authServerUrl and realm
      int realmsIndex = issuerUri.lastIndexOf("/realms/");
      if (realmsIndex != -1) {
        authServerUrl = issuerUri.substring(0, realmsIndex);
        realm = issuerUri.substring(realmsIndex + "/realms/".length());
      } else {
        throw new IllegalArgumentException("Invalid issuer-uri format: " + issuerUri);
      }
    }
  }

  public String getAuthServerUrl() {
    return authServerUrl;
  }

  public void setAuthServerUrl(String authServerUrl) {
    this.authServerUrl = authServerUrl;
  }

  public String getInternalServerUrl() {
    return internalServerUrl;
  }

  public void getInternalServerUrl(String internalServerUrl) {
    this.internalServerUrl = internalServerUrl;
  }

  public String getRealm() {
    return realm;
  }

  public void setRealm(String realm) {
    this.realm = realm;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public boolean isPublicClient() {
    return publicClient;
  }

  public void setPublicClient(boolean publicClient) {
    this.publicClient = publicClient;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public String getRealmUrl() {
    return String.format("%s/realms/%s", this.authServerUrl, this.realm);
  }

  public String getRealmCertsUrlInternal() {
    // internal Docker URL resolver to make sure reachable inside container
    return String.format("%s/realms/%s/protocol/openid-connect/certs",
        this.internalServerUrl, this.realm);
  }

  @Getter
  @Setter
  public static class Credentials {
    private String secret;
    private String username;
    private String password;

    public String getSecret() {
      return secret;
    }

    public void setSecret(String secret) {
      this.secret = secret;
    }
  }
}
