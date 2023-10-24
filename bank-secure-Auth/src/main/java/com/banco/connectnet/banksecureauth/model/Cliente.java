package com.banco.connectnet.banksecureauth.model;


import com.banco.connectnet.banksecureauth.model.enums.Genero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Cliente implements Serializable {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Genero genero;
    private String senha;

    private Set<Role> roles = new HashSet<>();


    public Cliente() {

    }
}
