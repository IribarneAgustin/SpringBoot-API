package com.pinapp.challenge.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "PinApp Challenge",
                version = "1.0",
                description = "Agustin Iribarne"
        ),
        servers = {
                @Server(url = "https://challenge-lively-forest-2663.fly.dev", description = "Production Server")
        }
)
@Configuration
public class OpenApiConfig {
}
