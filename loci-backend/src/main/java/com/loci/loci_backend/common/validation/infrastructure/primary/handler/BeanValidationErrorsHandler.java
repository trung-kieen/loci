package com.loci.loci_backend.common.validation.infrastructure.primary.handler;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Hanler error as the ProblemDetail object
 * Response as status code 400
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE - 10)
class BeanValidationErrorsHandler {
  // Validation errors specific details field, use Map<String, String>
  private static final String ERRORS = "errors";
  private static final Logger log = LoggerFactory.getLogger(BeanValidationErrorsHandler.class);

  /**
   * Build problem details error follow the RFC 7807 convention
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ProblemDetail handleMissingParameterException(MissingServletRequestParameterException ex) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    problem.setTitle("Missing or invalid request parameter");
    problem.setDetail(ex.getMessage());
    log.info(ex.getMessage(), ex);

    return problem;
  }

  /**
   * Validation fails on service-layer method parameters
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  // Get the validation eror to the map errors
  private Map<String, String> buildErrors(MethodArgumentNotValidException exception) {
    return exception
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toUnmodifiableMap(FieldError::getField, FieldError::getDefaultMessage));
  }

  // Perform create problem details with error field
  private ProblemDetail buildProblemDetail() {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        "One or more fields were invalid. See 'errors' for details.");

    problem.setTitle("Bean validation error");
    return problem;
  }

  private Map<String, String> buildErrors(ConstraintViolationException exception) {
    return exception
        .getConstraintViolations()
        .stream()
        .collect(Collectors.toUnmodifiableMap(toFieldName(), ConstraintViolation::getMessage));
  }

  private Function<ConstraintViolation<?>, String> toFieldName() {
    return error -> {
      String propertyPath = error.getPropertyPath().toString();

      return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    };
  }
}
