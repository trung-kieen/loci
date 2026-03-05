package com.loci.loci_backend.common.authentication.infrastructure.primary.filter;

import java.io.IOException;

import com.google.common.util.concurrent.RateLimiter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
  // limit requests/second
  private RateLimiter rateLimiter = RateLimiter.create(10.0);

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (!rateLimiter.tryAcquire()) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      return;
    }

    filterChain.doFilter(request, response);
  }
}
