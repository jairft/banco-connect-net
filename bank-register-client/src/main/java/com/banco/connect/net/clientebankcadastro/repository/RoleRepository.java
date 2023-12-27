package com.banco.connect.net.clientebankcadastro.repository;

import com.banco.connect.net.clientebankcadastro.entity.Role;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNomeRole(TipoRole tipoRole);

}
