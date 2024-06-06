package br.com.fiap.srvcliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server prdServer = new Server();
        prdServer.setUrl("https://app-srvclientes.onrender.com");
        prdServer.setDescription("Server Production");

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080/documentacao/clientes");
        devServer.setDescription("Server Development");


        Contact contact = new Contact();
        contact.setEmail("contato@gmail.com");
        contact.setName("Contato");

        Info info = new Info()
                .title("Tech Challenge Fase V")
                .version("1.0")
                .contact(contact)
                .description("Documentação dos endpoints da última fase. Fase V - Microserviços");


        return new OpenAPI().info(info).servers(List.of(prdServer, devServer));
    }
}
