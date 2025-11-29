package com.loci.loci_backend.common.validation.infrastructure.factory;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem.AccessDeniedProblemDetail;
import com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem.AuthenticationProblemDetail;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemDetailsFactory {
  private final ObjectMapper objectMapper;

  public AuthenticationProblemDetail createAuthenticationProblem(
      HttpServletRequest request, AuthenticationException ex, String requestId) {

    return AuthenticationProblemDetail.builder(getAuthDetail(ex), request.getRequestURI())
        // .traceId(getTraceId())
        .requestId(requestId)
        .errorCode(getAuthErrorCode(ex))
        .build();
  }

  public AccessDeniedProblemDetail createAccessDeniedProblem(
      HttpServletRequest request, AccessDeniedException ex, String requestId) {

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String user = auth != null ? auth.getName() : "anonymous";
    List<String> authorities = auth != null && auth.getAuthorities() != null
        ? auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        : List.of();

    return AccessDeniedProblemDetail.builder(request.getRequestURI())
        // .traceId(getTraceId())
        .requestId(requestId)
        .user(user, authorities)
        .requiredAuthority(ex.getMessage())
        .build();
  }

  private String getAuthDetail(AuthenticationException ex) {
    if (ex instanceof BadCredentialsException)
      return "Invalid credentials";
    if (ex instanceof InsufficientAuthenticationException)
      return "Full authentication is required";
    if (ex instanceof AccountStatusException)
      return "Account is locked or disabled";
    return "Authentication token is missing or invalid";
  }

  private String getAuthErrorCode(AuthenticationException ex) {
    if (ex instanceof BadCredentialsException)
      return "AUTH_BAD_CREDS";
    if (ex instanceof InsufficientAuthenticationException)
      return "AUTH_INSUFFICIENT";
    if (ex instanceof AccountStatusException)
      return "AUTH_ACCOUNT_INVALID";
    return "AUTH_INVALID_TOKEN";
  }

  // private String getTraceId() {
  // return Optional.ofNullable(tracer)
  // .map(Tracer::currentSpan)
  // .map(span -> span.context().traceId())
  // .orElse("no-trace");
  // }

  // @Autowired(required = false)
  // private Tracer tracer;
}
