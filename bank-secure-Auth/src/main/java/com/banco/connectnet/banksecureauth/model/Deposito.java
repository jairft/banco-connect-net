package com.banco.connectnet.banksecureauth.model;

import com.banco.connectnet.banksecureauth.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Deposito {

    private Integer numero;
    private Integer digito;
    private String agencia;
    private TipoConta tipoConta;
    private BigDecimal valor;
}
