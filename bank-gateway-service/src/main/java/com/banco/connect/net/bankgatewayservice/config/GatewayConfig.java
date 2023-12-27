package com.banco.connect.net.bankgatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("bank-register-client", r -> r
                        .path("/clientes/**")
                        .or()
                        .path("/contas/**")
                        .filters(f -> f
                                .rewriteResponseHeader("Cookie", ".*", "REDACTED")
                                .rewriteResponseHeader("Set-Cookie", ".*", "REDACTED"))
                        .uri("http://localhost:8081"))
                .route("bank-secure-auth", r -> r
                        .path("/auth/clientes/**")
                        .or()
                        .path("/auth/contas/**")
                        .or()
                        .path("/auth/transacoes/**")
                        .filters(f -> f
                                .rewriteResponseHeader("Cookie", ".*", "REDACTED")
                                .rewriteResponseHeader("Set-Cookie", ".*", "REDACTED"))
                        .uri("http://localhost:8082"))
                .route("bank-transactions-client", r -> r
                        .path("/transacoes/**")
                        .filters(f -> f
                                .rewriteResponseHeader("Cookie", ".*", "REDACTED")
                                .rewriteResponseHeader("Set-Cookie", ".*", "REDACTED"))
                        .uri("http://localhost:8083"))
                .build();
    }

}