package br.com.david.shared.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API DE AGENDAMENTO",
                version = "!.0",
                description = "API para gerenciamento de agendamentos"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        }
)

public class SwaggerConfig {
}
