package com.loci.loci_backend.common.wire.security.primary;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfiguration {


  private CorsConfiguration corsConfiguration;

  public CorsFilterConfiguration(CorsConfiguration corsConfiguration) {
    this.corsConfiguration = corsConfiguration;
  }


  @Bean
  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Use url base configuration => set cors from application  properties to config
    source.registerCorsConfiguration("/**", corsConfiguration);
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    // Set config in high order
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

}
