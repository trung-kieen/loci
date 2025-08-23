package com.loci.loci_backend.common.authentication.application;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

public class AuthenticatedUser {

  public static final String PREFERED_USERNAME = "email";

  private AuthenticatedUser() {
  }

  public static void authenticatedUser() {
    var attrributes = attributes();
    String firstname = attrributes.get("given_name").toString();
    String lastname = attrributes.get("family_name").toString();
    String email = attrributes.get("email").toString();
    Roles authorities = roles();
  }

  private static Optional<Authentication> authentication() {
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
  public static Optional<Username> optionalUsername() {
    return authentication().map(AuthenticatedUser::readPrincipal).flatMap(Username::of);
  }

  /**
   * Read user principal from authentication
   *
   * @param authentication authentication to read the principal from
   * @return The user principal
   * @throws UnknownAuthenticationException if the authentication can't be read
   *                                        (unknown token type)
   */
  public static String readPrincipal(Authentication authentication) {
    Assert.notNull("authentication", authentication());

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
  public static Roles roles() {
    return authentication().map(toRoles()).orElse(Roles.EMPTY);
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
  public static Map<String, Object> attributes() {
    Authentication token = authentication().orElseThrow(NotAuthenticatedUserException::new);
    if (token instanceof JwtAuthenticationToken jwtAuthenticationToken) {
      return jwtAuthenticationToken.getTokenAttributes();
    }
    throw new UnknowAuthenticationException();
  }

}
