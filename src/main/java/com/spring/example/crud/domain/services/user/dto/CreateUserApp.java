package com.spring.example.crud.domain.services.user.dto;

import com.spring.example.crud.domain.builders.UserBuilder;
import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.models.shared.HasEmail;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.spring.example.crud.domain.services.user.dto.CreateUserApi.STRONG_PASSWORD;
import static com.spring.example.crud.domain.services.user.dto.CreateUserApi.STRONG_PASSWORD_MESSAGE;
import static com.spring.example.crud.domain.validation.EmailValidations.validateEmailUniqueness;

@Data
public class CreateUserApp implements HasEmail {

    private static final String CONFIRM_ERROR_MESSAGE = "Valor informado na confirmação de senha invalido.";

    @NotEmpty(message = "Por favor, forneça um nome.")
    private String name;

    @NotEmpty(message = "Por favor, forneça um e-mail.")
    @Email(message = "Por favor, forneça um e-mail valido.")
    private String email;

    @NotEmpty(message = "Por favor, forneça uma senha.")
    @Pattern(regexp = STRONG_PASSWORD, message = STRONG_PASSWORD_MESSAGE)
    private String password;

    @NotEmpty(message = "Por favor, confirme a senha.")
    private String confirmPassword;

    public void validate(BindingResult result) {
        if (!getConfirmPassword().equals(getPassword())) {
            result.addError(new ObjectError("confirmPassowrd", CONFIRM_ERROR_MESSAGE));
        }
        
        validateEmailUniqueness(this, result);
    }
    
    public User user() {
        return new UserBuilder(name)
            .setEmail(email)
                .setPassword(password)
                    .build();
    }
}
