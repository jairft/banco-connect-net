package com.banco.connectnet.banksecureauth.controller;

import com.banco.connectnet.banksecureauth.model.Conta;
import com.banco.connectnet.banksecureauth.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastrar/{cpf}")
    public ResponseEntity<Mono<Conta>> cadastar(@RequestBody Conta conta, @PathVariable String cpf) {
        return ResponseEntity.ok(contaService.cadastrarConta(conta, cpf));
    }
}
