package com.banco.connect.net.clientebankcadastro.controller;

import com.banco.connect.net.clientebankcadastro.entity.Conta;
import com.banco.connect.net.clientebankcadastro.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastrar/{cpf}")
    public ResponseEntity<Conta> cadastar(@RequestBody Conta conta, @PathVariable String cpf) {
        return ResponseEntity.ok(contaService.cadastrar(conta, cpf));
    }
}
