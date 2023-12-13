package com.banco.connectnet.banksecureauth.model;


import com.banco.connectnet.banksecureauth.model.enums.Genero;
import com.banco.connectnet.banksecureauth.model.enums.TipoRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class Cliente implements UserDetails, Serializable {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private Genero genero;
    private String senha;
    private List<Conta> contas = new ArrayList<>();
    private Role role;

    public Cliente() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null) {
            if (this.role.getNomeRole() == TipoRole.ADMIN) {
                return Arrays.asList(
                        new SimpleGrantedAuthority("ADMIN"),
                        new SimpleGrantedAuthority("USER")
                );
            } else {
                return Collections.singletonList(new SimpleGrantedAuthority("USER"));
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
