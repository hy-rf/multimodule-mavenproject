package com.backend.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI(MessageSource messageSource) {
    String title = messageSource.getMessage("swagger.title", null, LocaleContextHolder.getLocale());
    String description = messageSource.getMessage("swagger.description", null, LocaleContextHolder.getLocale());
    return new OpenAPI()
        .info(new Info()
            .title(title)
            .description(description));
  }
}
