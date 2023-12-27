package com.banco.connect.net.banktransactionscliente.entity;

import com.banco.connect.net.banktransactionscliente.entity.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Conta> contas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    public Cliente() {

    }

    public void setCpf(String cpf) {
        if (cpf != null) {
            this.cpf = cpf.replaceAll("[.-]", "");
        } else {
            this.cpf = null;
        }
    }
}
