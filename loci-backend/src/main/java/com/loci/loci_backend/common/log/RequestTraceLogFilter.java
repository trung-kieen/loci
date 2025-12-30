package com.loci.loci_backend.common.log;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Provide context for each incoming request
 */
public class RequestTraceLogFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String requestId = UUID.randomUUID().toString();
    MDC.put("requestId", requestId);

    try {
      chain.doFilter(request, response);
    } finally {
      MDC.remove("requestId");
    }
  }

}
