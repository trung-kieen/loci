package com.loci.loci_backend.common.authentication.infrastructure.primary.entrypoint;

import java.io.IOException;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loci.loci_backend.common.validation.infrastructure.factory.ProblemDetailsFactory;
import com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem.AuthenticationProblemDetail;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  private final ObjectMapper objectMapper;
  private final ProblemDetailsFactory problemFactory;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    String requestId = UUID.randomUUID().toString().substring(0, 8);
    // long startTime = (Long) request.getAttribute("requestStartTime");

    log.error("""
        AUTHENTICATION FAILED [{}]
        Path: {} {}
        Exception: {} - {}
        Authorization Header: {}
        User-Agent: {}
        Remote IP: {}
        """,
        // Duration: {}ms
        requestId,
        request.getMethod(),
        request.getRequestURI(),
        authException.getClass().getSimpleName(),
        authException.getMessage(),
        getSafeAuthHeader(request),
        request.getHeader("User-Agent"),
        getClientIp(request),
        authException // Stack trace last
        // System.currentTimeMillis() - startTime,
    );

    response.setStatus(HttpStatus.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

    AuthenticationProblemDetail problem = problemFactory.createAuthenticationProblem(
        request, authException, requestId);

    // NOTE: dsiable in production
    String authHeader = request.getHeader("Authorization");
    log.debug("Authorization header present: {}", authHeader != null);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.warn("Missing or malformed Authorization header");
    }

    objectMapper.writeValue(response.getWriter(), problem);

  }

  private String getSafeAuthHeader(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    if (auth == null)
      return "MISSING";
    if (auth.length() < 20)
      return "MALFORMED";
    return auth.substring(0, 20) + "...";
  }

  private String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    return (ip != null) ? ip.split(",")[0] : request.getRemoteAddr();
  }
}
