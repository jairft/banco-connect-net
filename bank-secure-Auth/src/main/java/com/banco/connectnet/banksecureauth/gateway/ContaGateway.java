package com.banco.connectnet.banksecureauth.gateway;

import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.model.Conta;
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
public class ContaGateway {

    private static final Logger logger = LoggerFactory.getLogger(ContaGateway.class);
    @Autowired
    @Qualifier("webClient")
    private WebClient webClient;
    @Value("${cliente.conta}")
    private String urlCadastro;

    public Mono<Conta> cadastrarConta(Conta conta, String cpf) {
        return getWebClient()
                .post()
                .uri(getUrlCadastro(), cpf)
                .bodyValue(conta)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Conta.class)
                .onErrorResume(error -> {
                    logger.error("Erro ao tentar cadastrar conta. {}", error.getMessage());
                    return Mono.error(new CustomBadRequestException());
                });
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public String getUrlCadastro() {
        return urlCadastro;
    }
}
