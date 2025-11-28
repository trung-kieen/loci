package com.loci.loci_backend.common.user.domain.repository;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.KeycloakUser;

public interface KeycloakAdminRepository {
    void createUser(KeycloakUser adminUser);
    boolean userExists(Username username);
    void deleteAllUser();
}
