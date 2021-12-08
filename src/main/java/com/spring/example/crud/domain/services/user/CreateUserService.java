package com.spring.example.crud.domain.services.user;

import com.spring.example.crud.domain.models.shared.Type;
import com.spring.example.crud.domain.repositories.RoleRepository;
import com.spring.example.crud.domain.repositories.UserRepository;
import com.spring.example.crud.domain.services.user.dto.CreateUserApi;
import com.spring.example.crud.domain.services.user.dto.CreateUserApp;
import com.spring.example.crud.domain.services.user.dto.UserDetails;
import com.spring.example.crud.utils.Toasts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.spring.example.crud.utils.Responses.created;

@Service
public class CreateUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<UserDetails> create(CreateUserApi create) {

        create.validate();

        var body = create.user();

        var role = roleRepository.findOptionalByInitials("USER")
                .orElseThrow();

        body.setRoles(List.of(role));

        var user = userRepository.save(body);

        return created(new UserDetails(user), "users");
    }

    public String create(
        CreateUserApp create,
        BindingResult result,
        RedirectAttributes redirect,
        Model model
    ) {
        create.validate(result);
        
        if (result.hasErrors()) {
            model.addAttribute("user", create);
            Toasts.add(model, result);
            return "app/register/index";
        }

        var user = create.user();

        var role = roleRepository.findOptionalByInitials("USER")
                .orElseThrow();

        user.setRoles(List.of(role));

        userRepository.save(user);
        
        Toasts.add(redirect, "Cadastro realizado com sucesso.", Type.SUCCESS);
        
        return "redirect:/app/login";
    }
}
