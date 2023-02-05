package com.uasJava.uasJavaAnggi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Server localServer = new Server();
        localServer.url("http://localhost:8080");
        localServer.setDescription("local");

        return new OpenAPI()
                .servers(List.of(localServer))
                .info(new Info().title("Anggi Permana - School API")
                        .description("")
                        .version("v0.0.1")
                        .license(new License().name("Java TM").url("")));
    }
}
