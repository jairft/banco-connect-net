package com.banco.connect.net.clientebankcadastro.config;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.entity.Role;
import com.banco.connect.net.clientebankcadastro.entity.enums.Genero;
import com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole;
import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
import com.banco.connect.net.clientebankcadastro.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole.ROLE_ADMIN;
import static com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole.ROLE_USUARIO;

@Configuration
@Profile("local")
public class DataLoaderConfig {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ApplicationRunner dataLoader() {
        return args -> {
            // Inserir dados na tabela tb_role
            Role roleUsuario = new Role(1L, ROLE_USUARIO);
            Role roleAdmin = new Role(2L, ROLE_ADMIN);
            roleRepository.save(roleUsuario);
            roleRepository.save(roleAdmin);

            Set<Role> rolesClienteAdmin = new HashSet<>();
            rolesClienteAdmin.add(roleAdmin);

            Set<Role> rolesClienteUsuario = new HashSet<>();
            rolesClienteUsuario.add(roleUsuario);

            // Inserir dados na tabela tb_cliente
            Cliente cliente1 = new Cliente(1L, "Maria Silva", "maria@email.com", "(99)95-123-4567", "98765432101", Genero.FEMININO, "$2a$10$9sdf7t1ZqBl34DcCZUtCG8pLwv2FkqK4gFdmC4v9UWl.Hs7h6E3QU", rolesClienteUsuario);
            Cliente cliente2 = new Cliente(2L, "João Santos", "joao@email.com", "(65)95-987-6543", "12398745602", Genero.MASCULINO, "$2a$10$Fk9aCfB.kT42yBjDl37R4wsQ4N3n95ulVCAkwvYbS3t.Zl.KA7l3e", rolesClienteAdmin);
            cliente1.getRoles().add(roleUsuario);
            cliente2.getRoles().add(roleUsuario);
            cliente2.getRoles().add(roleAdmin);
            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
        };
    }
}
