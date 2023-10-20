package com.banco.connect.net.clientebankcadastro.entity;

import com.banco.connect.net.clientebankcadastro.entity.enums.Genero;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Genero genero;
    private String senha;



}
