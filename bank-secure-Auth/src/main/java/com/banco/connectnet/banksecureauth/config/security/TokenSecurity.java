package com.banco.connectnet.banksecureauth.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.banco.connectnet.banksecureauth.exception.CustomBadRequestException;
import com.banco.connectnet.banksecureauth.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class TokenSecurity {

    private static final Logger logger = LoggerFactory.getLogger(TokenSecurity.class);
    @Value("${security.token.secret}")
    private String secret;
    @Value("${spring.application.name}")
    private String applicationName;

    public String gerarToken(Cliente cliente) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(applicationName)
                    .withSubject(cliente.getEmail())
                    .withExpiresAt(getTempoExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            logger.error("Erro ao gerar o token {}", ex.getMessage());
            throw new CustomBadRequestException(ex.getMessage());
        }
    }

    public String validatarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            logger.error("Erro ao validar o token {}", ex.getMessage());
            return "";
        }
    }


    public Date getTempoExpiracao() {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(8);
        return Date.from(localDateTime.toInstant(ZoneOffset.of("-03:00")));
    }
}
