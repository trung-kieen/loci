package com.loci.loci_backend.common.validation.infrastructure.primary;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleGeneralException(Exception ex) {
      log.debug(ex);
    }


}
