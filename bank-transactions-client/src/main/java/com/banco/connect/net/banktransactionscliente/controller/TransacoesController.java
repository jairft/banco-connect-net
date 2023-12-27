package com.banco.connect.net.banktransactionscliente.controller;


import com.banco.connect.net.banktransactionscliente.entity.Conta;
import com.banco.connect.net.banktransactionscliente.entity.model.Deposito;
import com.banco.connect.net.banktransactionscliente.service.TransacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {

    @Autowired
    private TransacoesService transacoesService;

    @PostMapping("/deposito")
    public ResponseEntity<Conta> depositar(@RequestBody Deposito deposito){
        return ResponseEntity.ok(transacoesService.depositarValor(deposito));

    }

    @PostMapping("/saque")
    public ResponseEntity<Conta> sacar(@RequestBody Deposito deposito){
        return ResponseEntity.ok(transacoesService.sacarValor(deposito));

    }

}
