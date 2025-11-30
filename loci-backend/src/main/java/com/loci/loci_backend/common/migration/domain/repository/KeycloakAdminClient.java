package com.loci.loci_backend.common.migration.domain.repository;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;

public interface KeycloakAdminClient {
    void createUser(KeycloakUser adminUser);
    boolean userExists(Username username);
    void deleteAllUser();
}
