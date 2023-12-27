package com.banco.connectnet.banksecureauth.service;

import com.banco.connectnet.banksecureauth.gateway.ContaGateway;
import com.banco.connectnet.banksecureauth.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service

public class ContaService {

    @Autowired
    private ContaGateway contaGateway;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Mono<Conta> cadastrarConta(Conta conta, String cpf) {
        String encryptedPassword = passwordEncoder.encode(conta.getChave());
        conta.setChave(encryptedPassword);
        return contaGateway.cadastrarConta(conta, cpf);
    }
}
