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

package com.loci.loci_backend.common.authentication.infrastructure.primary.resolver;

import com.loci.loci_backend.common.authentication.application.AuthenticatedUser;
import com.loci.loci_backend.common.authentication.domain.CurrentUser;
import com.loci.loci_backend.common.authentication.infrastructure.secondary.repository.RestIdentityProvider;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

/**
 * Provide mvc resolver for custom princial in controller layer
 */
@RequiredArgsConstructor
@Component
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
  private final RestIdentityProvider identityRepository;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return
    // parameter.hasParameterAnnotation(AuthenticatedUser.class)
    // &&
    CurrentUser.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    AuthenticatedUser annotation = parameter.getParameterAnnotation(AuthenticatedUser.class);
    boolean required = (annotation != null) && annotation.required();

    if (required) {
      return identityRepository.currentPrincipal();
    }

    try {
      return identityRepository.currentPrincipal();
    } catch (Exception e) {
      return null;
    }

  }

}
