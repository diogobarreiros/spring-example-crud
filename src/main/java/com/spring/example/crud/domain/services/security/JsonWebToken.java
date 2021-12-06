package com.spring.example.crud.domain.services.security;

import com.spring.example.crud.domain.models.entity.Role;
import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.models.security.Authorized;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.example.crud.utils.Constants.SECURITY.ROLES_KEY_ON_JWT;


public class JsonWebToken {

    public String encode(User user, LocalDateTime expiration, String secret) {
        return encode(user.getId(), user.getRoles(), expiration, secret);
    }

    public String encode(Long id, List<Role> authorities, LocalDateTime expiration, String secret) {
        return Jwts.builder()
                .setSubject(id.toString())
                .claim(ROLES_KEY_ON_JWT, authorities
                        .stream()
                        .map(Role::getAuthority)
                        .collect(Collectors.joining(",")))
                .setExpiration(Date.from(expiration
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authorized decode(String token, String secret) {

        var decoded = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        var id = Long.parseLong(decoded.getBody().getSubject());
        var authorities = Arrays.stream(decoded.getBody().get(ROLES_KEY_ON_JWT).toString().split(",")).map(Role::new)
                .collect(Collectors.toList());

        return new Authorized(id, authorities);
    }
}
