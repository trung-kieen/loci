package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import java.time.Instant;

import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE)
class BusinessExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ProblemDetail handleNotFound(EntityNotFoundException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    log.warn("Rest handler a problem {}", problem);
    problem.setTitle("Resource not found");
    problem.setProperty("timestamp", Instant.now());
    return problem;
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    log.warn("Rest handler a problem {}", problem);
    problem.setTitle("Resource not found");
    problem.setProperty("timestamp", Instant.now());
    problem.setProperty("resource_id", ex.getResourceId());
    return problem;
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ProblemDetail handleDuplicate(DuplicateResourceException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
    problem.setTitle("Duplicate resource");
    problem.setDetail(ex.getMessage());
    problem.setProperty("conflicting_field", ex.getConflictingField());
    return problem;
  }

  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
  public ProblemDetail handleBusinessLogic(RuntimeException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    problem.setTitle("Business rule violation");
    problem.setDetail(ex.getMessage());
    return problem;
  }
}
