package com.banco.connect.net.clientebankcadastro.service;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.entity.Conta;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoConta;
import com.banco.connect.net.clientebankcadastro.exception.CustomBadRequestException;
import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
import com.banco.connect.net.clientebankcadastro.repository.ContaRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ContaService {

    private static final Logger logger = LoggerFactory.getLogger(ContaService.class);
    private final Gson gson = new Gson();
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public Conta cadastrar(Conta conta, String cpf) {
        try {
            Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
            Cliente cliente = clienteOptional.get();
            Optional<Conta> contaExistente = contaRepository.findByCliente(cliente);

            if (contaExistente.isPresent()) {
                if (!verificarTipoContaExistente(clienteOptional.get(), conta.getTipoConta())) {
                    conta.setNumero(contaExistente.get().getNumero());
                    conta.setDigito(contaExistente.get().getDigito());
                    conta.setSaldo(BigDecimal.ZERO);
                    conta.setCliente(cliente);
                    return contaRepository.save(conta);
                }
            } else if (Boolean.FALSE.equals(clienteOptional.get().getCpf().isEmpty())) {
                conta.setNumero(gerarNumeroConta());
                conta.setDigito(gerarDigito());
                conta.setSaldo(BigDecimal.ZERO);
                conta.setCliente(cliente);
                return contaRepository.save(conta);
            }
        } catch (RuntimeException ex) {
            logger.error("Cliente não encontrado para o CPF fornecido.");
            throw new CustomBadRequestException(ex);
        }
        return new Conta();
    }


    public boolean verificarTipoContaExistente(Cliente cliente, TipoConta tipoConta) {
        List<Conta> contasDoClienteETipo = contaRepository.findByClienteAndTipoConta(cliente, tipoConta);

        boolean existeTipoContaDesejado = contasDoClienteETipo.stream()
                .anyMatch(conta -> conta.getTipoConta() == TipoConta.CONTA_POUPANCA || conta.getTipoConta() == TipoConta.CONTA_CORRENTE);

        if (existeTipoContaDesejado) {
            logger.error("Já existe uma conta do mesmo tipo {} para este cliente {}.", tipoConta, cliente.getNome().toUpperCase());
            throw new CustomBadRequestException();
        }

        return false;
    }

    private Integer gerarNumeroConta() {

        Random random = new Random();
        return 100000 + random.nextInt(90000000);
    }

    private Integer gerarDigito() {
        Random random = new Random();
        return random.nextInt(10);
    }
}
