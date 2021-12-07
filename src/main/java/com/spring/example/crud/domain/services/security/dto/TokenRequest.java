package com.spring.example.crud.domain.services.security.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TokenRequest {

    @NotNull(message = "O Email não pode ser NULO.")
    @NotEmpty(message = "Email invalido.")
    private String email;

    @NotNull(message = "A Senha não pode ser NULA.")
    @NotEmpty(message = "Senha invalida.")
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.email);
        return hash;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TokenRequest other = (TokenRequest) obj;
        return Objects.equals(this.email, other.email);
    }
}
