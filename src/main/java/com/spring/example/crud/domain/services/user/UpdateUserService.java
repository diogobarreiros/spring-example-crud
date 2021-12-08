package com.spring.example.crud.domain.services.user;

import com.spring.example.crud.domain.models.entity.Role;
import com.spring.example.crud.domain.repositories.RoleRepository;
import com.spring.example.crud.domain.repositories.UserRepository;
import com.spring.example.crud.domain.services.user.dto.UpdateUser;
import com.spring.example.crud.domain.services.user.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.spring.example.crud.domain.services.security.SecurityService.authorized;
import static com.spring.example.crud.domain.validation.EmailValidations.validateEmailUniquenessOnModify;
import static com.spring.example.crud.utils.Responses.*;

@Service
public class UpdateUserService {

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<UserDetails> update(Long id, UpdateUser body) {

        authorized()
            .filter(authorized -> authorized.cantModify(id))
                .orElseThrow(() -> unauthorized("Permissão invalida atualização de recurso"));

        var actual = repository
            .findOptionalByIdAndDeletedAtIsNullFetchRoles(id)
                .orElseThrow(() -> notFound("Usuário não encontrado"));

        var roles = body.getRoles();
        List<Role> newRoles = new ArrayList<>();
        if (!roles.isEmpty()) {
            roles.forEach(role -> {
                var newRole = roleRepository.findOptionalById(role.getId()).orElseThrow(() -> notFound("Regra não encontrada"));
                newRoles.add(newRole);
            });
            body.setRoles(newRoles);
        }
        
        validateEmailUniquenessOnModify(body, actual);

        actual.merge(body);

        return ok(new UserDetails(repository.save(actual)));
    }
}
