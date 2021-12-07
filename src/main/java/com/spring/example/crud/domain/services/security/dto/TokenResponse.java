package com.spring.example.crud.domain.services.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.example.crud.domain.models.entity.RefreshToken;
import com.spring.example.crud.domain.models.entity.User;
import com.spring.example.crud.domain.services.user.dto.UserDetails;

import java.time.LocalDateTime;

public class TokenResponse {    
    private final UserDetails user;
    private final String token;
    private final RefreshToken refreshToken;
    private final LocalDateTime expiresIn;

    public TokenResponse(
        User user,
        String token,
        RefreshToken refreshToken,
        LocalDateTime expiresIn
    ) {
        this.user = new UserDetails(user);
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public UserDetails getUser() {
        return user;
    }

    @JsonProperty("access_token")
    public String getToken() {
        return token;
    }

    @JsonProperty("refresh_token")
    public String getRefresh() {
        return refreshToken.getCode();
    }

    @JsonFormat(shape = Shape.STRING)
    @JsonProperty("expires_in")
    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }

    @JsonProperty("token_type")
    public String getTokenType() {
        return "Bearer";
    }
}
