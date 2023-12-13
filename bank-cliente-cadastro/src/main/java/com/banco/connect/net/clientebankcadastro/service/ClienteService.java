package com.banco.connect.net.clientebankcadastro.service;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.entity.Role;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole;
import com.banco.connect.net.clientebankcadastro.exception.CustomBadRequestException;
import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
import com.banco.connect.net.clientebankcadastro.repository.RoleRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole.ADMIN;
import static com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole.USER;

@Service
@AllArgsConstructor
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository;
    private final RoleRepository roleRepository;
    private final Gson gson = new Gson();

    public Cliente findByIdCliente(Long id) {
        return clienteRepository.findById(id).get();
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmailOrCpf(cliente.getEmail(), cliente.getCpf());
        if (clienteOptional.isPresent()) {
            logger.error("Email e/ou CPF já cadastrados no banco.");
            throw new CustomBadRequestException();
        }
        if (cliente.getRole() == null || cliente.getRole().getNomeRole() == null) {
            // Recupera a role padrão do banco de dados
            Role rolePadrao = roleRepository.findByNomeRole(USER);
            if (rolePadrao == null) {
                throw new CustomBadRequestException("Role padrão não encontrado no banco de dados.");
            }
            cliente.setRole(rolePadrao);
        } else if (cliente.getRole().getNomeRole() == TipoRole.ADMIN) {
            Role roleAdmin = roleRepository.findByNomeRole(ADMIN);
            cliente.setRole(roleAdmin);
        }
            logger.info("Cadastrando cliente: {}", gson.toJson(cliente));
            cliente.setCpf(cliente.getCpf());
            return clienteRepository.save(cliente);

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
