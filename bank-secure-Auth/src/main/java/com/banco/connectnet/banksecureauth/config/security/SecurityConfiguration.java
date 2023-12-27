package com.banco.connectnet.banksecureauth.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.banco.connectnet.banksecureauth.model.enums.TipoRole.ADMIN;
import static com.banco.connectnet.banksecureauth.model.enums.TipoRole.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String URL_REGISTRO = "/auth/clientes/registro";
    public static final String URL_LOGIN = "/auth/clientes/login";
    public static final String URL_CADASTRAR_CONTA = "/auth/contas/cadastrar/{cpf}";
    public static final String URL_PROCURAR_CLIENTE = "/auth/clientes/procurar/{email}";
    public static final String URL_DEPOSITO = "/auth/transacoes/deposito";
    public static final String URL_SAQUE = "/auth/transacoes/saque";
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers(HttpMethod.POST, URL_REGISTRO).permitAll()
                        .antMatchers(HttpMethod.POST, URL_LOGIN).permitAll()
                        .antMatchers(HttpMethod.POST, URL_DEPOSITO).permitAll()
                        .antMatchers(HttpMethod.POST, URL_SAQUE).hasAuthority(USER.name())
                        .antMatchers(HttpMethod.POST, URL_CADASTRAR_CONTA).hasAuthority(ADMIN.name())
                        .antMatchers(HttpMethod.GET, URL_PROCURAR_CLIENTE).hasAuthority(ADMIN.name()) // Aplique a política de autorização para a rota específica
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
