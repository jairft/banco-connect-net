package com.banco.connect.net.clientebankcadastro.repository;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.entity.Conta;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {


    List<Conta> findByClienteAndTipoConta(Cliente cliente, TipoConta tipoConta);

    Optional<Conta> findByCliente(Cliente cliente);

}
