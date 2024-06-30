package br.com.fiap.srvgateway.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",

            "/clientes/swagger-ui/**",
            "/clientes/swagger-ui.html",
            "/clientes/api-docs",

            "/itens/swagger-ui/**",
            "/itens/swagger-ui.html",
            "/itens/api-docs",

            "/auth/swagger-ui/**",
            "/auth/swagger-ui.html",
            "/auth/api-docs",

            "/users/swagger-ui.html",
            "/users/api-docs"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}