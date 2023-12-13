package com.banco.connectnet.banksecureauth.controller;


import com.banco.connectnet.banksecureauth.config.security.TokenSecurity;
import com.banco.connectnet.banksecureauth.model.Cliente;
import com.banco.connectnet.banksecureauth.model.LoginCliente;
import com.banco.connectnet.banksecureauth.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    private ClienteService service;
    @Autowired
    private TokenSecurity tokenSecurity;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/procurar/{email}")
    public ResponseEntity<Mono<Cliente>> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginCliente login) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
        try {
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);

            String email = ((User) auth.getPrincipal()).getUsername();

            Cliente cliente = service.findByEmail(email).block();
            String token = tokenSecurity.gerarToken(cliente);
            logger.info("Autenticação bem-sucedida para o usuário: {}", email);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            logger.error("Autenticação falhou para o usuário: {}", login.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Autenticação falhou");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<Mono<Cliente>> cadastrarCliente(@RequestBody Cliente cliente) {
        Mono<Cliente> clienteCadastrado = service.cadastrar(cliente);
        return ResponseEntity.ok(clienteCadastrado);
    }
}
