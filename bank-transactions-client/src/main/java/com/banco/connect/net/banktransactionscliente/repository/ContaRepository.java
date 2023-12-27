package com.banco.connect.net.banktransactionscliente.repository;

import com.banco.connect.net.banktransactionscliente.entity.Conta;
import com.banco.connect.net.banktransactionscliente.entity.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {



    Optional<Conta> findByNumeroAndDigitoAndAgenciaAndTipoConta(int numero,
                                                               int digito,
                                                               String agencia,
                                                               TipoConta tipoConta);
}
