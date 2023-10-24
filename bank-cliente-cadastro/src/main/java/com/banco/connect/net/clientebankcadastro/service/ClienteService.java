package com.banco.connect.net.clientebankcadastro.service;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.exception.CustomBadRequestException;
import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    public Cliente findByIdCliente(Long id){
        return clienteRepository.findById(id).get();
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
