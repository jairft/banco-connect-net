package com.banco.connectnet.banksecureauth.gateway;

import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ClienteGateway {

    @Autowired
    @Qualifier("webClient")
    private WebClient webClient;
    @Value("${cliente.auth}")
    private String authCliente;

    private static final Logger logger = LoggerFactory.getLogger(ClienteGateway.class);
    public Mono<Cliente> autenticarCliente(String email){
        return getWebClient()
                .get()
                .uri(getAuthCliente(), email)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Cliente.class)
                .onErrorResume(error -> {
                    logger.error("Email não encontrado: {}", email);
                    logger.error("Erro ao buscar cliente por email: {}", error.getMessage());
                    return Mono.error(new CustomBadRequestException());
                });


    }

    public WebClient getWebClient() {
        return webClient;
    }

    public String getAuthCliente() {
        return authCliente;
    }
}
