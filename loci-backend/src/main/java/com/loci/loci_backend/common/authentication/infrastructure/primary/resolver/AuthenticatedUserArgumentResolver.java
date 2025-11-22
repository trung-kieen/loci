package com.loci.loci_backend.common.authentication.infrastructure.primary.resolver;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.application.AuthenticatedUser;
import com.loci.loci_backend.common.authentication.application.NotAuthenticatedUserException;
import com.loci.loci_backend.common.authentication.infrastructure.secondary.port.RestIdentityRepository;
import com.loci.loci_backend.common.websocket.domain.aggregate.KeycloakPrincipal;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {
  private final RestIdentityRepository identityRepository;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return
    parameter.hasParameterAnnotation(AuthenticatedUser.class)
        &&
    KeycloakPrincipal.class.isAssignableFrom(parameter.getParameterType());
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
