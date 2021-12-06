package com.spring.example.crud.utils;

import com.spring.example.crud.domain.models.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.spring.example.crud.utils.Constants.SECURITY.JWT;

public class TokenUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ACCEPTABLE_TOKEN_TYPE = "Bearer ";

    /**
     * Recupera o token de dentro do cabeçalho da request.
     * 
     * @param request {request que chegou na API}
     * @return null se o token estiver vazio ou for do tipo errado.
     */
    public static String authorization(HttpServletRequest request) {
        var token = request.getHeader(AUTHORIZATION_HEADER);

        if (tokenIsNull(token))
            return null;

        return token.substring(7);
    }

    /**
     * Verifica se o token esta vazio ou não é do tipo 'Bearer'.
     * 
     * @return true se o token for vazio ou não for bearer.
     */
    private static boolean tokenIsNull(String token) {
        return Objects.isNull(token) || !token.startsWith(ACCEPTABLE_TOKEN_TYPE);
    }

    public static String token(Integer expiration, String secret) {
        return token(expiration, secret, List.of(new Role("ADM")));
    }

    public static String token(Integer expiration, String secret, List<Role> roles) {
        var token = JWT.encode(1L, roles, LocalDateTime.now().plusHours(expiration), secret);
        return String.format("Bearer %s", token);
    }
}