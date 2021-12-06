package com.spring.example.crud.domain.services.user.dto;

import com.spring.example.crud.domain.models.shared.HasEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateUser implements HasEmail {
    
    @NotNull(message = "O nome não pode ser NULL.")
    @NotEmpty(message = "Por favor, forneça um nome.")
    private String name;
    
    @NotNull(message = "O e-mail não pode ser NULL.")
    @Email(message = "Por favor, forneça um e-mail valido.")
    private String email;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
