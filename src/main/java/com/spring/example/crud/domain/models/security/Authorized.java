package com.spring.example.crud.domain.models.security;

import com.spring.example.crud.domain.models.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.List;

public class Authorized extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;

    public Authorized(Long id, List<Role> authorities) {
        super("USERNAME", "SECRET", authorities);
        this.id = id;
    }

    public Authorized(com.spring.example.crud.domain.models.entity.User user) {
        super(
            user.getEmail(),
            user.getPassword(),
            user.isActive(),
            true,
            true,
            true,
            user.getRoles()
        );
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

    public UsernamePasswordAuthenticationToken getAuthentication() {
        return new UsernamePasswordAuthenticationToken(this, null, getAuthorities());
    }

    public Boolean isAdmin() {
        return getAuthorities()
            .stream()
                .anyMatch((role) -> role.getAuthority().equals("ADM"));
    }

    public Boolean cantModify(Long id) {
        var admin = isAdmin();
        var equals = getId().equals(id);
        if (admin) {
            return true;
        }
        return equals;
    }

    public Boolean cantRead(Long id) {
        var admin = isAdmin();
        var equals = getId().equals(id);
        if (admin) {
            return true;
        }
        return equals;
    }
 
    @Override
    public String toString() {
        return getId().toString();
    }
}