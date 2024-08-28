package com.sisd.sisd.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI myOpenAPI(){
    
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Server Url in local environment");

        Server prodServer = new Server();
        prodServer.setUrl("https://sisd-api.com");
        prodServer.setDescription("Server Url in production environment");

        Contact contact = new Contact();
        contact.setEmail("fadilah@gmail.com");
        contact.setName("sisd");
        contact.setUrl("https://www.test.com");

        License mitLicense =new License()
        .name("MIT License")
        .url("https://test.com");

        Info info = new Info()
        .title("Aplikasi SISD of Java")
        .version("1.0")
        .contact(contact)
        .description("This API is use for SISD course of java")
        .termsOfService("https://www.test.com/terms")
        .license(mitLicense);

        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");

        return new OpenAPI().components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .info(info)
                .servers(List.of(localServer, prodServer));
    }
}
