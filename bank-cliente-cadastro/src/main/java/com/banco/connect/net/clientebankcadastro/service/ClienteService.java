package com.banco.connect.net.clientebankcadastro.service;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.entity.Role;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole;
import com.banco.connect.net.clientebankcadastro.exception.CustomBadRequestException;
import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
import com.banco.connect.net.clientebankcadastro.repository.RoleRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private final RoleRepository roleRepository;
    private final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    public Cliente findByIdCliente(Long id){
        return clienteRepository.findById(id).get();
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmailAndCpf(cliente.getEmail(), cliente.getCpf());
        if (!clienteOptional.isPresent()) {
            // Verifica se o cliente não possui roles definidas.
            if (cliente.getRoles() == null || cliente.getRoles().isEmpty()) {
                // Recupera o papel padrão do banco de dados.
                Role rolePadrao = roleRepository.findByNomeRole(TipoRole.ROLE_USUARIO);

                if (rolePadrao != null) {
                    cliente.setRoles(Collections.singleton(rolePadrao));
                } else {
                    throw new CustomBadRequestException("Role padrão não encontrado no banco de dados.");
                }
            }
            logger.info("Cadastrando cliente: {}", gson.toJson(cliente));
            return clienteRepository.save(cliente);
        } else {
            logger.error("Email e/ou CPF já cadastrados no banco.");
            throw new CustomBadRequestException();
        }
    }


    public Optional<Cliente> buscarByEmail(String email) {
        Optional<Cliente> optionalEmail = clienteRepository.findByEmail(email);
        try {
            if (!optionalEmail.get().getEmail().isEmpty()) {
                return Optional.of(optionalEmail.get());
            }
        } catch (Exception ex) {
            logger.error("Erro ao tentar buscar email: {}", ex.getMessage());
            throw new CustomBadRequestException();

        }
        return Optional.empty();
    }

}
