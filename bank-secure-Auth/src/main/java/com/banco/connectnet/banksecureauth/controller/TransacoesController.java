package com.banco.connectnet.banksecureauth.controller;

import com.banco.connectnet.banksecureauth.model.Cliente;
import com.banco.connectnet.banksecureauth.model.Conta;
import com.banco.connectnet.banksecureauth.model.Deposito;
import com.banco.connectnet.banksecureauth.model.Saque;
import com.banco.connectnet.banksecureauth.service.ClienteService;
import com.banco.connectnet.banksecureauth.service.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/auth/transacoes")
public class TransacoesController {

    @Autowired
    private TransacoesService service;
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/deposito")
    public ResponseEntity<Mono<Conta>> depositar(@RequestBody Deposito deposito){
        return ResponseEntity.ok(service.depositar(deposito));
    }

    @PostMapping("/saque")
    public ResponseEntity<Conta> sacar(@RequestBody Saque saque) {

        Conta contaAtualizada = service.sacar(saque).block();


        return ResponseEntity.ok(contaAtualizada);
    }
}
