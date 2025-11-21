package com.loci.loci_backend.common.authentication.infrastructure.primary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
  private String authServerUrl;
  private String realm;
  private String resource;
  private boolean publicClient;
  private Credentials credentials = new Credentials();

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

  // Getters and Setters
  public String getAuthServerUrl() {
    return authServerUrl;
  }

  public void setAuthServerUrl(String authServerUrl) {
    this.authServerUrl = authServerUrl;
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

  public static class Credentials {
    private String secret;

    public String getSecret() {
      return secret;
    }

    public void setSecret(String secret) {
      this.secret = secret;
    }
  }
}
