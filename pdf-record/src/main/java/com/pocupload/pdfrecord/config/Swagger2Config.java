package com.pocupload.pdfrecord.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger2Config {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Digital Library Power Plant API Suite")
                        .description("Documentation Of Sample API")
                        .version("v1.0")
                        .contact(new Contact().name("Tech Shell").email("techshell01@gmail.com").url("https://www.techshell.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Full API Documentation")
                        .url("https://example.com/docs"));
        
    }
}