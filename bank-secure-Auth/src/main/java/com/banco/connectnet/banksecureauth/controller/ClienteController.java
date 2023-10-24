package com.banco.connectnet.banksecureauth.controller;


import com.banco.connectnet.banksecureauth.model.Cliente;
import com.banco.connectnet.banksecureauth.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/procurar/{email}")
    public ResponseEntity<Mono<Cliente>> findByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.findByEmail(email));
    }
}
