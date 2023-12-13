package com.banco.connect.net.banktransactionscliente.service;

import com.banco.connect.net.banktransactionscliente.entity.Conta;
import com.banco.connect.net.banktransactionscliente.entity.model.DepositoSaque;
import com.banco.connect.net.banktransactionscliente.exception.CustomBadRequestException;
import com.banco.connect.net.banktransactionscliente.repository.ContaRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransacoesService {

    private static final Logger logger = LoggerFactory.getLogger(TransacoesService.class);
    @Autowired
    private ContaRepository contaRepository;
    private final Gson gson = new Gson();
    public Conta depositar(DepositoSaque conta){
        Optional<Conta> optionalDepositoSaque = contaRepository.
                findByNumeroAndDigitoAndAgenciaAndTipoConta(conta.getNumero(), conta.getDigito(), conta.getAgencia(), conta.getTipoConta());
        if (optionalDepositoSaque.isPresent()){
            logger.info("Deposito realizado para conta {}", gson.toJson(conta));
            BigDecimal novoSaldo = optionalDepositoSaque.get().getSaldo().add(conta.getValor());
            optionalDepositoSaque.get().setSaldo(novoSaldo);
            return contaRepository.save(optionalDepositoSaque.get());
        }
        else {
            logger.error("ERRO ao tentar depositar valor na conta.");
            throw new CustomBadRequestException();
        }
    }
}
