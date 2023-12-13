//package com.banco.connect.net.clientebankcadastro.config;
//
//import com.banco.connect.net.clientebankcadastro.entity.Cliente;
//import com.banco.connect.net.clientebankcadastro.entity.Role;
//import com.banco.connect.net.clientebankcadastro.entity.enums.Genero;
//import com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole;
//import com.banco.connect.net.clientebankcadastro.repository.ClienteRepository;
//import com.banco.connect.net.clientebankcadastro.repository.RoleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import static com.banco.connect.net.clientebankcadastro.entity.enums.TipoRole.*;
//
//@Configuration
//@Profile("local")
//public class DataLoaderConfig {
//
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Bean
//    public ApplicationRunner dataLoader() {
//        return args -> {
//            // Inserir dados na tabela tb_role
//            Role roleUsuario = new Role(1L, USUARIO);
//            Role roleAdmin = new Role(2L, ADMIN);
//            roleRepository.save(roleUsuario);
//            roleRepository.save(roleAdmin);
//
//            Set<Role> rolesClienteAdmin = new HashSet<>();
//            rolesClienteAdmin.add(roleAdmin);
//
//            Set<Role> rolesClienteUsuario = new HashSet<>();
//            rolesClienteUsuario.add(roleUsuario);
//
//            // Inserir dados na tabela tb_cliente
//            Cliente cliente1 = new Cliente(1L, "Maria Silva", "maria@email.com", "(99)95-123-4567", "98765432101", Genero.FEMININO, "$2a$10$VF66xW8d75kpmvPTKPqt9.mamq3osaegLYIiley8gw.3v70He28UC", rolesClienteUsuario);
//            Cliente cliente2 = new Cliente(2L, "João Santos", "joao@email.com", "(65)95-987-6543", "12398745602", Genero.MASCULINO, "$2a$10$5mfqgpS7epcgJh3cKKVpjeZYtUM4IMaR5du1NNlluHxdmBcR1tin2", rolesClienteAdmin);
//            cliente1.getRoles().add(roleUsuario);
//            cliente2.getRoles().add(roleUsuario);
//            cliente2.getRoles().add(roleAdmin);
//            clienteRepository.save(cliente1);
//            clienteRepository.save(cliente2);
//        };
//    }
//}
