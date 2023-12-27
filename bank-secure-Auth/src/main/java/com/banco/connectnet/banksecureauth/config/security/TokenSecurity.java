package com.banco.connectnet.banksecureauth.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
                    .withClaim("email", cliente.getEmail())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            logger.error("Erro ao gerar o token {}", ex.getMessage());
            throw new CustomBadRequestException(ex.getMessage());
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(applicationName)
                    .build()
                    .verify(token);

            // Obtém o email do usuário do token
            String userEmail = decodedJWT.getClaim("email").asString();

            return userEmail;
        } catch (JWTVerificationException ex) {
            logger.error("Erro ao validar o token {}", ex.getMessage());
            return null; // Retorna null em caso de falha na validação
        }
    }


    public Date getTempoExpiracao() {
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(8);
        return Date.from(localDateTime.toInstant(ZoneOffset.of("-03:00")));
    }
}
