package com.banco.connect.net.banktransactionscliente.entity;

import com.banco.connect.net.banktransactionscliente.entity.enums.TipoRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_role", unique = true)
    private TipoRole nomeRole;


    public Role() {

    }

}



