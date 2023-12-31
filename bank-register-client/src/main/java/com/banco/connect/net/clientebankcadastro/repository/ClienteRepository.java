package com.banco.connect.net.clientebankcadastro.repository;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByEmailOrCpf(String email, String cpf);

    Optional<Cliente> findByCpf(String cpf);

}
