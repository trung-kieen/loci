package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

/**
 * Security relate exception, handle with 401 or 403
 */
@ControllerAdvice
@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE - 10)
public class SecurityErrorHandler {

  @ExceptionHandler(AccessDeniedException.class)
  ProblemDetail handlerAccessDenied(AccessDeniedException exception) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN); // 403
    problem.setTitle("Access denied");
    problem.setDetail("Insufficient permissions to access this resource");
    log.info(exception.getMessage(), exception);
    return problem;
  }

  @ExceptionHandler(AuthenticationException.class)
  public ProblemDetail handleAuthentication(AuthenticationException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED); // 401
    problem.setTitle("Authentication required");
    problem.setDetail(ex.getMessage());
    log.info(ex.getMessage(), ex);
    return problem;
  }

}
