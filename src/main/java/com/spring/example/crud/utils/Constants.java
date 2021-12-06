package com.spring.example.crud.utils;

import com.spring.example.crud.domain.services.security.JsonWebToken;

public class Constants {
    public static class SECURITY {
        public static final JsonWebToken JWT = new JsonWebToken();
        public static final Integer DAY_MILLISECONDS = 86400;

        public static final String ROLES_KEY_ON_JWT = "roles";
        public static final String INVALID_USERNAME = "Nome de usuário invalido.";

        public static final String USERNAME_PARAMETER = "email";
        public static final String PASSWORD_PARAMETER = "password";
        public static final String HOME_URL = "/app";
        public static final String LOGIN_URL = "/app/login";
        public static final String LOGIN_ERROR_URL = LOGIN_URL + "?error=true";
        public static final String ACESSO_NEGADO_URL = LOGIN_URL + "?denied=true";
        public static final String LOGOUT_URL = "/app/logout";

        public static final String SESSION_COOKIE_NAME = "JSESSIONID";

        public static final String[] STATIC_FILES = {
                "/robots.txt",
                "/font/**",
                "/css/**",
                "/webjars/**",
                "/webjars/",
                "/js/**",
                "/favicon.ico",
                "/**.html",
                "/documentation/**"
        };
    }
}
