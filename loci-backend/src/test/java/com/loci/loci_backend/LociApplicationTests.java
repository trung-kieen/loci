package com.loci.loci_backend;

import com.loci.loci_backend.common.authentication.infrastructure.primary.keycloak.KeycloakProperties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootTest(classes = { KeycloakProperties.class, CorsConfiguration.class })
@ExtendWith(SpringExtension.class)
// @Import({KeycloakProperties.class, CorsConfiguration.class})
// @EnableConfigurationProperties({KeycloakProperties.class
// ,CorsConfiguration.class})
// @ComponentScan(basePackages = "com.loci.loci_backend")
// @AutoConfigureMockMvc
class LociApplicationTests {

  @Test
  void contextLoads() {
  }

}
