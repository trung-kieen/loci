package com.loci.loci_backend.common.wire.log.primary;

import com.loci.loci_backend.common.log.RequestTraceLogFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilterConfiguration {

  @Bean
  public FilterRegistrationBean<RequestTraceLogFilter> requestLogFilter() {
    FilterRegistrationBean<RequestTraceLogFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new RequestTraceLogFilter());
    bean.addUrlPatterns("/*");
    return bean;
  }

}
