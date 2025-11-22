package com.loci.loci_backend.test;

import com.loci.loci_backend.common.authentication.application.AuthenticatedUser;
import com.loci.loci_backend.common.authentication.domain.KeycloakPrincipal;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebController {

  private static final Logger logger = LoggerFactory.getLogger(WebController.class);

  private final UserService userService;


  @GetMapping(path = "/userInfo1")
  public ResponseEntity<StringResponse> getUserInfo1() {
    User user = userService.getAuthenticatedUser();
    return ResponseEntity
        .ok(new StringResponse(user.getFirstname() + " " + user.getLastname() + ", " + user.getEmail()));
  }

  @GetMapping("/userInfo2")
  public ResponseEntity<StringResponse> getUserInfo2( KeycloakPrincipal keycloakPrincipal) {
    System.out.println(keycloakPrincipal);

    JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    String firstname = auth.getTokenAttributes().get("given_name").toString();
    String lastname = auth.getTokenAttributes().get("family_name").toString();
    String email = auth.getTokenAttributes().get("email").toString();
    String authorities = auth.getAuthorities().toString();

    return ResponseEntity.ok(new StringResponse(firstname + " " + lastname + ", " + email + ", " + authorities));
  }

}
