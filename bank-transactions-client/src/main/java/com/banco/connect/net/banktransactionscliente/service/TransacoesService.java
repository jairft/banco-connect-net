package com.banco.connect.net.banktransactionscliente.service;

import com.banco.connect.net.banktransactionscliente.entity.Conta;
import com.banco.connect.net.banktransactionscliente.entity.model.Deposito;
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
    public Conta depositarValor(Deposito deposito){
        Optional<Conta> optionalDeposito = contaRepository.
                findByNumeroAndDigitoAndAgenciaAndTipoConta(deposito.getNumero(), deposito.getDigito(), deposito.getAgencia(), deposito.getTipoConta());
        if (optionalDeposito.isPresent()){
            logger.info("Deposito realizado para deposito {}", gson.toJson(deposito));
            BigDecimal novoSaldo = optionalDeposito.get().getSaldo().add(deposito.getValor());
            optionalDeposito.get().setSaldo(novoSaldo);
            return contaRepository.save(optionalDeposito.get());
        }
        else {
            logger.error("ERRO ao tentar depositar valor na deposito.");
            throw new CustomBadRequestException();
        }
    }

    public Conta sacarValor(Deposito saque) {
        Optional<Conta> optionalConta = contaRepository
                .findByNumeroAndDigitoAndAgenciaAndTipoConta(
                        saque.getNumero(),
                        saque.getDigito(),
                        saque.getAgencia(),
                        saque.getTipoConta()
                );

        if (optionalConta.isPresent()) {
            Conta conta = optionalConta.get();
            BigDecimal saldoAtual = conta.getSaldo();
            BigDecimal valorSaque = saque.getValor();
            if (saldoAtual.compareTo(valorSaque) >= 0) {
                logger.info("Saque realizado para conta {}", gson.toJson(saque));
                BigDecimal novoSaldo = saldoAtual.subtract(valorSaque);
                conta.setSaldo(novoSaldo);

                contaRepository.save(conta);

                return conta;
            } else {
                throw new CustomBadRequestException("Saldo insuficiente para realizar o saque");
            }
        } else {
            throw new CustomBadRequestException("Conta não encontrada");
        }
    }
}
