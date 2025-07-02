package com.loci.loci_backend.security;


import java.io.IOException;

import com.loci.loci_backend.user.User;
import com.loci.loci_backend.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtUserSyncFilter extends OncePerRequestFilter {

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try {
      JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

      String firstname = String.valueOf(token.getTokenAttributes().get("given_name"));
      String lastname = String.valueOf(token.getTokenAttributes().get("family_name"));
      String email = String.valueOf(token.getTokenAttributes().get("email"));
      User.Gender gender = token.getTokenAttributes().get("gender") == null ? null :
              User.Gender.valueOf(String.valueOf(token.getTokenAttributes().get("gender")).toUpperCase());

      User user = User.builder()
              .firstname(firstname)
              .lastname(lastname)
              .email(email)
              .gender(gender)
              .build();

      userService.syncUser(user);
    } catch (Exception e) {
      throw new IllegalArgumentException("Unable to auth user");
    }

    filterChain.doFilter(request, response);
  }

}
