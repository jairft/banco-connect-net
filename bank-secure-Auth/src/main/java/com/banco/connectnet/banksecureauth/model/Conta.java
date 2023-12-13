package com.banco.connectnet.banksecureauth.model;


import com.banco.connectnet.banksecureauth.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

@AllArgsConstructor

public class Conta {


    private Long id;
    private Integer numero;
    private Integer digito;
    private String agencia;
    private TipoConta tipoConta;
    private BigDecimal saldo;

    private Cliente cliente;

    public Conta() {

    }


}
