package com.banco.connectnet.banksecureauth.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginCliente {

    private String email;
    private String senha;
}
