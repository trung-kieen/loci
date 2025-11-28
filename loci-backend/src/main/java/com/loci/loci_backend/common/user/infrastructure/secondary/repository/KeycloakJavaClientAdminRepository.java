package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.Arrays;
import java.util.Collections;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakProperties;
import com.loci.loci_backend.common.user.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.repository.KeycloakAdminRepository;
import com.loci.loci_backend.common.user.infrastructure.secondary.exceptions.KeycloakMigrateException;
import com.loci.loci_backend.core.user.infrastructure.primary.resource.UserResource;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;

@Service
public class KeycloakJavaClientAdminRepository implements KeycloakAdminRepository {

  private final Keycloak keycloak;
  private final KeycloakProperties properties;

  public KeycloakJavaClientAdminRepository(KeycloakProperties properties) {
    this.properties = properties;
    this.keycloak = KeycloakBuilder.builder()
        .serverUrl(properties.getAuthServerUrl())
        // .realm(properties.getRealm()) // Auth realm
        .realm("master") // Auth realm
        .username(properties.getCredentials().getUsername())
        .password(properties.getCredentials().getPassword())
        .clientId("admin-cli")
        .grantType(OAuth2Constants.PASSWORD)
        // .resteasyClient(new ResteasyClientBuilder()
        // .connectionPoolSize(20)
        // .build())
        .build();
  }

  @Override
  public void createUser(KeycloakUser user) {
    RealmResource realmResource = keycloak.realm(properties.getRealm());
    UsersResource usersResource = realmResource.users();

    UserRepresentation userRep = new UserRepresentation();
    userRep.setUsername(user.getUsername().username());
    userRep.setEmail(user.getEmail().value());
    userRep.setFirstName(user.getFirstName().value());
    userRep.setLastName(user.getLastName().value());
    userRep.setEnabled(true);
    userRep.setEmailVerified(true);

    // Set password
    // CredentialRepresentation userCredential = new CredentialRepresentation();
    // userCredential.setType(CredentialRepresentation.PASSWORD);
    // userCredential.setValue(user.getPassword().value());
    // userCredential.setTemporary(false);

    CredentialRepresentation defaultCredential = new CredentialRepresentation();
    defaultCredential.setType(CredentialRepresentation.PASSWORD);
    defaultCredential.setValue(properties.getMigrationPassword());
    defaultCredential.setTemporary(false);

    // userRep.setCredentials(Arrays.asList(userCredential, defaultCredential));
    userRep.setCredentials(Arrays.asList(defaultCredential));

    Response response = usersResource.create(userRep);
    if (response.getStatus() != 201) {
      String error = response.readEntity(String.class);
      throw new KeycloakMigrateException("Failed to create user: " + error);
    }
    response.close();
  }

  @Override
  public boolean userExists(Username username) {
    return !keycloak.realm(properties.getRealm())
        .users()
        .search(username.username(), true)
        .isEmpty();
  }

  @Override
  public void deleteAllUser() {
    RealmResource realmResource = keycloak.realm(properties.getRealm());
    UsersResource usersResource = realmResource.users();
    for (UserRepresentation user : usersResource.list()) {
      usersResource.delete(user.getId());
    }

  }

}
