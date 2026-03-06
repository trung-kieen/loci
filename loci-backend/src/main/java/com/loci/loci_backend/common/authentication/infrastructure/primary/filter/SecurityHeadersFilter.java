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

package com.loci.loci_backend.common.authentication.infrastructure.primary.filter;

// import java.io.IOException;

// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class SecurityHeadersFilter extends OncePerRequestFilter {

//   @Override
//   protected void doFilterInternal(HttpServletRequest request,
//       HttpServletResponse response, FilterChain filterChain)
//       throws ServletException, IOException {

//     response.setHeader("X-Content-Type-Options", "nosniff");
//     // Avoid use iframe
//     response.setHeader("X-Frame-Options", "DENY");
//     // // Browser support already
//     // response.setHeader("X-XSS-Protection", "1; mode=block");
//     // // Require https request
//     // response.setHeader("Strict-Transport-Security", "max-age=31536000;
//     // includeSubDomains");
//     response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
//     response.setHeader("Pragma", "no-cache");

//     filterChain.doFilter(request, response);
//   }
// }
