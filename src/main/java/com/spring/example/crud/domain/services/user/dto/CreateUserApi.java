package com.spring.example.crud.domain.services.user.dto;

import com.spring.example.crud.domain.builders.UserBuilder;
import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.models.shared.HasEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.spring.example.crud.domain.validation.EmailValidations.validateEmailUniqueness;

@Data
public class CreateUserApi implements HasEmail {

    public static final String DEFAULT_PASSWORD = "mudar123";
    public static final String STRONG_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
    public static final String STRONG_PASSWORD_MESSAGE = "No mínimo 8 caracteres, com no mínimo um número, um caractere especial, uma letra maiúscula e uma letra minúscula.";

    @NotEmpty(message = "Por favor, forneça um nome.")
    private String name;
    
    @NotEmpty(message = "Por favor, forneça um e-mail.")
    @Email(message = "Por favor, forneça um e-mail valido.")
    private String email;

    @NotEmpty(message = "Por favor, forneça uma senha.")
    @Pattern(regexp = STRONG_PASSWORD, message = STRONG_PASSWORD_MESSAGE)
    private String password = DEFAULT_PASSWORD;

    public CreateUserApi(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    public void validate() {
        validateEmailUniqueness(this);
    }
    
    public User user() {
        return new UserBuilder(name)
            .setEmail(email)
                .setPassword(password)
                    .build();
    }
}
