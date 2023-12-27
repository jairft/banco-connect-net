package com.banco.connectnet.banksecureauth.gateway;

import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.model.Conta;
import com.banco.connectnet.banksecureauth.model.Deposito;
import com.banco.connectnet.banksecureauth.model.Saque;
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
public class TransacoesGateway {

    @Autowired
    @Qualifier("webClient")
    private WebClient webClient;

    @Value("${transacoes.deposito}")
    private String urlDeposito;
    @Value("${transacoes.saque}")
    private String urlSaque;

    private static final Logger logger = LoggerFactory.getLogger(TransacoesGateway.class);

    public Mono<Conta> realizarDeposito(Deposito deposito){
        return webClient
                .post()
                .uri(urlDeposito)
                .bodyValue(deposito)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Conta.class)
                .onErrorResume(error -> {
                    logger.error("Erro ao tentar fazer deposito. {}", error.getMessage());
                    return Mono.error(new CustomBadRequestException());
                })
                .doOnSuccess(response -> {
                    logger.info("Transacao realizado com sucesso!");
                });
    }

    public Mono<Conta> realizarSaque(Saque saque){
        return webClient
                .post()
                .uri(urlSaque)
                .bodyValue(saque)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Conta.class)
                .onErrorResume(error -> {
                    logger.error("Erro ao tentar fazer saque. {}", error.getMessage());
                    return Mono.error(new CustomBadRequestException());
                })
                .doOnSuccess(response -> {
                    logger.info("Saque realizado com sucesso!");
                });
    }


}
