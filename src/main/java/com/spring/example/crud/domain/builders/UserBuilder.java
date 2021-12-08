package com.spring.example.crud.domain.builders;

import com.spring.example.crud.domain.models.entity.Role;
import com.spring.example.crud.domain.models.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    
    private final User user;
    private List<Role> roles = new ArrayList<>();

    public static UserBuilder createUser(String name) {
        return new UserBuilder(name);
    }

    public UserBuilder(String name) {
        this.user = new User();
        this.user.setName(name);
    }

    public UserBuilder(String name, List<Role> roles) {
        this.user = new User();
        this.roles = roles;
        this.user.setName(name);
    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder setId(Long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder setActive(Boolean active) {
        user.setActive(active);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder addRole(Role role) {
        roles.add(role);
        return this;
    }

    public UserBuilder addRole(Long id) {
        roles.add(new Role(id));
        return this;
    }

    public User build() {
        user.setRoles(roles);
        return user;
    }
}