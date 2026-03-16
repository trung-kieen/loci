/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import java.time.Instant;

import com.loci.loci_backend.common.validation.domain.DomainViolationException;
import com.loci.loci_backend.common.validation.domain.DuplicateResourceException;
import com.loci.loci_backend.common.validation.domain.ResourceNotFoundException;
import com.loci.loci_backend.core.messaging.domain.exception.UserIsBlockedByOtherException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

/**
 * Lower of order = higher of priority
 */
@ControllerAdvice
@Log4j2
@Order(1)
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


  @ExceptionHandler(UserIsBlockedByOtherException.class)
  public ProblemDetail handleBlockedContact(UserIsBlockedByOtherException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
    problem.setTitle("User not found");
    log.warn("User is current blocked {}", ex.getCause());
    problem.setDetail(ex.getMessage());
    return problem;
  }


  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
  public ProblemDetail handleIllegalArgument(RuntimeException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    problem.setTitle("Badd argument violate business rule");
    problem.setDetail(ex.getMessage());
    return problem;
  }

  @ExceptionHandler({ DomainViolationException.class })
  public ProblemDetail handleBussinessLogic(DomainViolationException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Business logic violation");
    problem.setDetail(ex.getMessage());
    return problem;
  }

}
