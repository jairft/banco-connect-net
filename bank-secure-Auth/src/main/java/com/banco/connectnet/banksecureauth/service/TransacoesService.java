package com.banco.connectnet.banksecureauth.service;


import com.banco.connectnet.banksecureauth.gateway.TransacoesGateway;
import com.banco.connectnet.banksecureauth.model.Conta;
import com.banco.connectnet.banksecureauth.model.Deposito;
import com.banco.connectnet.banksecureauth.model.Saque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransacoesService {

    @Autowired
    private TransacoesGateway gateway;
    @Autowired
    private AuthenticationManager authenticationManager;
    public Mono<Conta> depositar(Deposito deposito){

        return gateway.realizarDeposito(deposito);
    }

    public Mono<Conta> sacar(Saque saque){
        return gateway.realizarSaque(saque);
    }
}
