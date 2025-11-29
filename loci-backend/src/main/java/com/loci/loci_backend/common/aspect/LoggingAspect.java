package com.loci.loci_backend.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * Log centerlize for common aspect of application
 * NOTE:
 * 1. AOP only proxies public methods, so don't try to use @Service + private constructor
 * 2. Use INFO or above for com.loci.loci_backend.common.aspect.LoggingAspect
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {
  // private static final Logger log =
  // LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("@annotation(Loggable)")
  public void loggableMethods() {
  }

  // Point cut all service
  @Pointcut("within(@org.springframework.stereotype.Service *)")
  public void serviceLayer() {
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  // public void restLayer(){}
  // @Pointcut("within(@org.springframework.stereotype.Controller *)")
  public void controllerLayer() {
  }

  @Pointcut("within(@org.springframework.messaging.handler.annotation.MessageMapping *)")
  // public void restLayer(){}
  // @Pointcut("within(@org.springframework.stereotype.Controller *)")
  public void messagingMethod() {
  }

  @Before("serviceLayer() || controllerLayer() || loggableMethods() || messagingMethod()")
  public void logMethodEntry(JoinPoint joinPoint) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    Object[] args = joinPoint.getArgs();
    log.info("Entering: {}.{}() with arguments: {}",
        className, methodName, args);
  }

  @AfterReturning(pointcut = "loggableMethods() || serviceLayer()", returning = "result")
  public void methodReturning(JoinPoint joinPoint, Object result) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();
    log.info("Method return with result: {}.{}() with result {}",
        className, methodName, result);
  }

  @Pointcut("@annotation(LogExecutionTime)")
  public void logExecutionTimeAnnotation() {
  }

  @Around("logExecutionTimeAnnotation()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    String methodName = joinPoint.getSignature().getName();

    try {
      Object result = joinPoint.proceed();
      long executionTime = System.currentTimeMillis() - start;

      log.info("Method {} executed in {} ms", methodName, executionTime);
      return result;
    } catch (Exception e) {
      long executionTime = System.currentTimeMillis() - start;
      log.error("Method {} failed after {} ms", methodName, executionTime);
      throw e;
    }
  }

}
