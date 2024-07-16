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

            "/clientes/v3/api-docs/**",
            "/clientes/api-docs/**",
            "/clientes/swagger-resources/**",
            "/clientes/swagger-ui/**",
            "/clientes/swagger-ui/swagger-initializer.js",
            "/clientes/swagger-ui/swagger-ui.css.map",
            "/clientes/swagger-ui/swagger-ui-bundle.js.map",
            "/clientes/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/clientes/swagger-ui/favicon-32x32.png",
            "/clientes/api-docs/swagger-config",
            "/clientes/api-docs",
            "/clientes/swagger-ui/index.html",
            "/clientes/swagger-ui/index.html/**",

            "/itens/v3/api-docs/**",
            "/itens/api-docs/**",
            "/itens/swagger-resources/**",
            "/itens/swagger-ui/**",
            "/itens/swagger-ui/swagger-initializer.js",
            "/itens/swagger-ui/swagger-ui.css.map",
            "/itens/swagger-ui/swagger-ui-bundle.js.map",
            "/itens/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/itens/swagger-ui/favicon-32x32.png",
            "/itens/api-docs/swagger-config",
            "/itens/api-docs",
            "/itens/swagger-ui/index.html",
            "/itens/swagger-ui/index.html/**",
            "/itens/swagger-ui/swagger-ui-bundle.js",
            "/itens/swagger-ui/swagger-ui-standalone-preset.js",
            "/itens/swagger-ui/index.css",
            "/itens/swagger-ui/swagger-ui.css",

            "/carrinhos/v3/api-docs/**",
            "/carrinhos/api-docs/**",
            "/carrinhos/swagger-resources/**",
            "/carrinhos/swagger-ui/**",
            "/carrinhos/swagger-ui/swagger-initializer.js",
            "/carrinhos/swagger-ui/swagger-ui.css.map",
            "/carrinhos/swagger-ui/swagger-ui-bundle.js.map",
            "/carrinhos/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/carrinhos/swagger-ui/favicon-32x32.png",
            "/carrinhos/api-docs/swagger-config",
            "/carrinhos/api-docs",
            "/carrinhos/swagger-ui/index.html",
            "/carrinhos/swagger-ui/index.html/**",
            "/carrinhos/swagger-ui/swagger-ui-bundle.js",
            "/carrinhos/swagger-ui/swagger-ui-standalone-preset.js",
            "/carrinhos/swagger-ui/index.css",
            "/carrinhos/swagger-ui/swagger-ui.css",

            "/pagamento/v3/api-docs/**",
            "/pagamento/api-docs/**",
            "/pagamento/swagger-resources/**",
            "/pagamento/swagger-ui/**",
            "/pagamento/swagger-ui/swagger-initializer.js",
            "/pagamento/swagger-ui/swagger-ui.css.map",
            "/pagamento/swagger-ui/swagger-ui-bundle.js.map",
            "/pagamento/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/pagamento/swagger-ui/favicon-32x32.png",
            "/pagamento/api-docs/swagger-config",
            "/pagamento/api-docs",
            "/pagamento/swagger-ui/index.html",
            "/pagamento/swagger-ui/index.html/**",
            "/pagamento/swagger-ui/swagger-ui-bundle.js",
            "/pagamento/swagger-ui/swagger-ui-standalone-preset.js",
            "/pagamento/swagger-ui/index.css",
            "/pagamento/swagger-ui/swagger-ui.css",

            "/users/v3/api-docs/**",
            "/users/api-docs/**",
            "/users/swagger-resources/**",
            "/users/swagger-ui/**",
            "/users/swagger-ui/swagger-initializer.js",
            "/users/swagger-ui/swagger-ui.css.map",
            "/users/swagger-ui/swagger-ui-bundle.js.map",
            "/users/swagger-ui/swagger-ui-standalone-preset.js.map",
            "/users/swagger-ui/favicon-32x32.png",
            "/users/api-docs/swagger-config",
            "/users/api-docs",
            "/users/swagger-ui/index.html",
            "/users/swagger-ui/index.html/**",
            "/users/swagger-ui/swagger-ui-bundle.js",
            "/users/swagger-ui/swagger-ui-standalone-preset.js",
            "/users/swagger-ui/index.css",
            "/users/swagger-ui/swagger-ui.css",

            "/auth/v3/api-docs/**",
            "/auth/api-docs/**",
            "/auth/swagger-resources/**",
            "/auth/swagger-ui/**",
            "/auth/swagger-ui/favicon-16x16.png",
            "/auth/swagger-ui/favicon-32x32.png",
            "/auth/api-docs/swagger-config",
            "/auth/api-docs",
            "/auth/swagger-ui/index.html",
            "/auth/swagger-ui/index.html/**",
            "/auth/swagger-ui/index.css",
            "/auth/swagger-ui/swagger-ui.css",
            "/auth/swagger-ui/swagger-ui-bundle.js",
            "/auth/swagger-ui/swagger-initializer.js",
            "/auth/swagger-ui/swagger-ui-standalone-preset.js"

            );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}