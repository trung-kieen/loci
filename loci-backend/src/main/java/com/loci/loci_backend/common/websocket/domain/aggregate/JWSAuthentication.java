package com.loci.loci_backend.common.websocket.domain.aggregate;

import java.util.Collection;

import com.loci.loci_backend.common.websocket.domain.vo.BearerToken;

import org.jilt.Builder;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWSAuthentication extends AbstractAuthenticationToken implements Authentication {
  private BearerToken bearerToken;
  private KeycloakPrincipal keycloakUser;
  private static final long serialVersionUID = 1L;

  public JWSAuthentication(BearerToken credential, KeycloakPrincipal principal,
      Collection<GrantedAuthority> authorities) {
    super(authorities);
    this.bearerToken = credential;
    this.keycloakUser = principal;
  }

  public JWSAuthentication(BearerToken bearerToken) {
    this(bearerToken, null, null);
  }

  @Override
  public Object getCredentials() {
    return bearerToken.token();
  }

  /**
   * Usage by inject Principal from context
   * public ChatMessage sendMessage(@Payload ChatMessage message, Principal
   * principal) {
   * JWSAuthentication auth = (JWSAuthentication) principal;
   * KeycloakUser keycloakUser = auth.getKeycloakUser();
   */
  @Override
  public Object getPrincipal() {
    return keycloakUser;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return super.getAuthorities();
  }
}
