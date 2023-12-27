package com.banco.connectnet.banksecureauth.model;


import com.banco.connectnet.banksecureauth.model.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Conta implements Serializable {


    private Long id;
    private Integer numero;
    private Integer digito;
    private String agencia;
    private TipoConta tipoConta;
    private BigDecimal saldo;
    private String chave;
    @JsonIgnore
    private Cliente cliente;

    public Conta() {

    }


}
