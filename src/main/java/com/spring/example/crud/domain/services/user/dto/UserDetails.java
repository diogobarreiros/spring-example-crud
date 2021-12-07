package com.spring.example.crud.domain.services.user.dto;

import com.spring.example.crud.domain.models.entity.Role;
import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.models.shared.Entity;

import java.util.List;

public class UserDetails implements Entity {
    private final Long id;
    private final String name;
    private final String email;
    private final List<String> roles;

    public UserDetails(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();

        this.roles = user.getRoles()
            .stream()
                .map(Role::getAuthority)
                    .toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
