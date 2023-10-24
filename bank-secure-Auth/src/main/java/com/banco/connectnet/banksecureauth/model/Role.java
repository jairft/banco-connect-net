package com.banco.connectnet.banksecureauth.model;

import com.banco.connectnet.banksecureauth.model.enums.TipoRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class Role implements Serializable {


    private Long id;
    private TipoRole nomeRole;

    public Role() {

    }
}
