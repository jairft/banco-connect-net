package com.banco.connectnet.banksecureauth.service;


import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.gateway.ClienteGateway;
import com.banco.connectnet.banksecureauth.model.Cliente;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@AllArgsConstructor
public class ClienteService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteGateway gateway;
    private final PasswordEncoder passwordEncoder;
    private final Gson gson = new Gson();

    public Mono<Cliente> findByEmail(String email) {
        return gateway.buscarClientePorEmail(email)
                .doOnNext(cliente -> {
                    if (cliente != null) {
                        logger.info("Email encontrado: {}", email);
                        logger.info("Email correspondente ao cliente: {}", gson.toJson(cliente));
                    }
                });
    }

    public Mono<Cliente> cadastrar(Cliente cliente) {
        String encryptedPassword = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(encryptedPassword);
        return gateway.cadastrar(cliente)
                .switchIfEmpty(Mono.error(new CustomBadRequestException()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return gateway.buscarClientePorEmail(username)
                .flatMap(cliente -> {
                    if (cliente != null) {
                        logger.info("Email encontrado: {}", username);
                        GrantedAuthority authority = new SimpleGrantedAuthority(cliente.getRole().getNomeRole().name());
                        return Mono.just(User.withUsername(cliente.getEmail())
                                .password(cliente.getSenha())
                                .authorities(Collections.singleton(authority))
                                .build());
                    }
                    return Mono.empty();
                }).block();
    }


}