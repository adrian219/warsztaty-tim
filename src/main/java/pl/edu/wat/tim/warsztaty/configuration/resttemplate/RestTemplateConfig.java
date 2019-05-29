package pl.edu.wat.tim.warsztaty.configuration.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  RestTemplate restTemplate(@Value("${github.login}") String login, @Value("${github.password}") String password, RestTemplateBuilder builder) {
    return builder
            .basicAuthentication(login, password)
            .build();
  }
}
