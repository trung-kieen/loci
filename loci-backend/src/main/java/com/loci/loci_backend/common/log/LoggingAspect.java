package com.loci.loci_backend.common.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Log centerlize for common aspect of application
 * NOTE:
 * 1. AOP only proxies public methods, so don't try to use log private
 * constructor or private method
 * 2. Use INFO or above for com.loci.loci_backend.common.aspect.LoggingAspect
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {

  @Pointcut("within(@Loggable *)")
  public void loggableClassMethods() {
  }

  @Pointcut("@within(Loggable)")
  public void loggableMetaAnnotatedClass() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.ApplicationService)")
  public void applicationServiceLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainService)")
  public void domainServiceLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryPort)")
  public void primaryPortLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort)")
  public void secondaryPortLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.PrimaryMapper)")
  public void primaryMapperLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryMapper)")
  public void secondaryMapperLayer() {
  }

  @Pointcut("@within(com.loci.loci_backend.common.ddd.infrastructure.stereotype.DomainMapper)")
  public void domainMapperLayer() {
  }

  // Combined pointcut for all DDD layers
  @Pointcut("applicationServiceLayer() || domainServiceLayer() || " +
      "primaryPortLayer() || secondaryPortLayer() || " +
      "primaryMapperLayer() || secondaryMapperLayer() || domainMapperLayer()")
  public void allDddLayers() {
  }

  @Pointcut("loggableClassMethods() || loggableMetaAnnotatedClass() || loggableMethod() || allDddLayers() ")
  public void allLoggable() {
  }

  @Pointcut("@annotation(Loggable)")
  public void loggableMethod() {
  }

  @Pointcut("within(@org.springframework.stereotype.Service *)")
  public void serviceLayer() {
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void controllerLayer() {
  }

  @Pointcut("@annotation(org.springframework.messaging.handler.annotation.MessageMapping)")
  public void messagingMethod() {
  }

  @Before("serviceLayer() || controllerLayer() || messagingMethod() || allLoggable()")
  public void logMethodEntry(JoinPoint joinPoint) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();

    log.info("Entering: {}.{}() with arguments: {}",
        className, methodName, sanitizeArgs(args));
  }

  @AfterReturning(pointcut = "serviceLayer() || allLoggable()", returning = "result")
  public void logMethodReturning(JoinPoint joinPoint, Object result) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info("Exiting: {}.{}() with result: {}",
        className, methodName, sanitizeResult(result));
  }

  @AfterThrowing(pointcut = "serviceLayer() || allLoggable()", throwing = "exception")
  public void logMethodException(JoinPoint joinPoint, Throwable exception) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.error("Exception in: {}.{}() - {}: {}",
        className, methodName, exception.getClass().getSimpleName(),
        exception.getMessage(), exception);
  }

  @Pointcut("@annotation(LogExecutionTime)")
  public void logExecutionTimeAnnotation() {
  }

  @Around("logExecutionTimeAnnotation()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    try {
      Object result = joinPoint.proceed();
      long executionTime = System.currentTimeMillis() - start;
      log.info("Performance: {}.{}() executed in {} ms",
          className, methodName, executionTime);
      return result;
    } catch (Throwable e) {
      long executionTime = System.currentTimeMillis() - start;
      log.error("Performance: {}.{}() failed after {} ms",
          className, methodName, executionTime);
      throw e;
    }
  }

  private Object sanitizeArgs(Object[] args) {
    if (args == null || args.length == 0) {
      return "[]";
    }
    return java.util.Arrays.toString(args);
  }

  private Object sanitizeResult(Object result) {
    return result;
  }
}
