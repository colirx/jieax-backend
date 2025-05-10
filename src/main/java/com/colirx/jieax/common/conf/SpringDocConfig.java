package com.colirx.jieax.common.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI buildOpenApi() {
        return new OpenAPI().info(buildOpenApiInfo());
    }

    private Info buildOpenApiInfo() {
        return new Info()
            .contact(buildOpenApiInfoContact());
            // .title();
    }

    private Contact buildOpenApiInfoContact() {
        return null;
    }
}
