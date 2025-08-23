package com.loci.loci_backend.common.validation.infrastructure.primary;

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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Rest error handle => Use as primary
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1000)
class BeanValidationErrorsHandler {

  private static final String ERRORS = "errors";
  private static final Logger log = LoggerFactory.getLogger(BeanValidationErrorsHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  //  Get the validation eror to the map errors
  private Map<String, String> buildErrors(MethodArgumentNotValidException exception) {
    return exception
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .collect(Collectors.toUnmodifiableMap(FieldError::getField, FieldError::getDefaultMessage));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  // Perform create problem details with error field
  private ProblemDetail buildProblemDetail() {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      "One or more fields were invalid. See 'errors' for details."
    );

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
