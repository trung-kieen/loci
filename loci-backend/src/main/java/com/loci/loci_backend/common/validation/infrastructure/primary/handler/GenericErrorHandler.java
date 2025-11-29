package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE - 10)
class GenericExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGeneric(Exception ex) {
    log.error("Unhandled exception", ex);
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problem.setTitle("Internal server error");
    problem.setDetail("An unexpected error occurred");
    problem.setProperty("trace_id", UUID.randomUUID().toString());
    return problem;
  }
}
