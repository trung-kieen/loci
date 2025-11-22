package com.loci.loci_backend.common.authentication.infrastructure.secondary.repository;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.application.NotAuthenticatedUserException;
import com.loci.loci_backend.common.authentication.application.UnknowAuthenticationException;
import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.authentication.domain.Role;
import com.loci.loci_backend.common.authentication.domain.Roles;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.validation.domain.Assert;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class RestIdentityRepository {

  public static final String PREFERED_USERNAME = "email";

  private RestIdentityRepository() {
  }

  public KeycloakPrincipal currentPrincipal() {
    var attrributes = attributes();
    Roles authorities = roles();
    return KeycloakPrincipal.fromTokenAttribute(attrributes , authorities);
  }

  public Authentication authentication() {
    return optionalAuthentication().orElseThrow(UnknowAuthenticationException::new);
  }

  public Optional<Authentication> optionalAuthentication() {
    JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    return Optional.ofNullable(auth);
  }

  /**
   * Get the authenticated user username
   *
   * @return The authenticated user username or empty if the user is not
   *         authenticated
   * @throws UnknownAuthenticationException if the user uses an unknown
   *                                        authentication scheme
   */
  public Optional<Username> optionalUsername() {
    return optionalAuthentication().map(this::readUsername).flatMap(Username::of);
  }

  /**
   * Read user principal from authentication
   *
   * @param authentication authentication to read the principal from
   * @return The user principal
   * @throws UnknownAuthenticationException if the authentication can't be read
   *                                        (unknown token type)
   */
  private String readUsername(Authentication authentication) {
    Assert.notNull("authentication", optionalAuthentication());

    if (authentication.getPrincipal() instanceof UserDetails details) {
      return details.getUsername();
    }
    if (authentication instanceof JwtAuthenticationToken token) {
      token.getToken().getClaims().get(PREFERED_USERNAME);
    }

    if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
      return (String) oidcUser.getAttributes().get(PREFERED_USERNAME);
    }

    if (authentication.getPrincipal() instanceof String pricipal) {
      return pricipal;
    }
    throw new UnknowAuthenticationException();
  }

  /**
   * Return Roles of authenticated user
   */
  public Roles roles() {
    return optionalAuthentication().map(toRoles()).orElse(Roles.EMPTY);
  }

  /**
   * Helper to convert role string in keycloak to Roles object
   */
  private static Function<Authentication, Roles> toRoles() {
    return authentication -> new Roles(authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).map(Role::from).collect(Collectors.toSet()));
  }

  /**
   * Get the authenticated user token attributes
   *
   * @return The authenticated user token attributes
   * @throws NotAuthenticatedUserException  if the user is not authenticated
   * @throws UnknownAuthenticationException if the authentication scheme is
   *                                        unknown
   */
  private Map<String, Object> attributes() {
    Authentication token = optionalAuthentication().orElseThrow(NotAuthenticatedUserException::new);
    if (token instanceof JwtAuthenticationToken jwtAuthenticationToken) {
      return jwtAuthenticationToken.getTokenAttributes();
    }
    throw new UnknowAuthenticationException();
  }

}
