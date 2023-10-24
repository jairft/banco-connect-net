package com.banco.connectnet.banksecureauth.service;


import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.gateway.ClienteGateway;
import com.banco.connectnet.banksecureauth.model.Cliente;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class ClienteService {

    @Autowired
    private ClienteGateway gateway;
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final Gson gson = new Gson();
    public Mono<Cliente> findByEmail(String email) {
        return gateway.autenticarCliente(email)
                .doOnNext(cliente -> {
                    if (cliente != null) {
                        logger.info("Email encontrado: {}", email);
                        logger.info("Email corresponde ao cliente: {}", gson.toJson(cliente));
                    }
                });
    }
}
