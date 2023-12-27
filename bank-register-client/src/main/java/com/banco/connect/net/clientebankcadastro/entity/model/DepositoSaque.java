package com.banco.connect.net.clientebankcadastro.entity.model;

import com.banco.connect.net.clientebankcadastro.entity.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class DepositoSaque {

    private Integer numero;
    private Integer digito;
    private String agencia;
    private TipoConta tipoConta;
    private BigDecimal valor;
}
