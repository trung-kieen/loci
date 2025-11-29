package com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loci.loci_backend.common.validation.infrastructure.factory.ProblemDetailsFactory;
import com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem.AccessDeniedProblemDetail;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthorizationEntryPointImpl implements AccessDeniedHandler {
  private final ProblemDetailsFactory problemFactory;
  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    String requestId = UUID.randomUUID().toString().substring(0, 8);

    // Log authorization failure with security context
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    log.warn("""
        ACCESS DENIED [{}]
        Path: {} {}
        User: {} (Authorities: {})
        Required: {}
        Message: {}
        """,
        requestId,
        request.getMethod(),
        request.getRequestURI(),
        auth != null ? auth.getName() : "anonymous",
        auth != null ? auth.getAuthorities() : "none",
        accessDeniedException.getMessage(),
        accessDeniedException.getCause());

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

    AccessDeniedProblemDetail problem = problemFactory.createAccessDeniedProblem(
        request, accessDeniedException, requestId);

    objectMapper.writeValue(response.getWriter(), problem);
  }

}
