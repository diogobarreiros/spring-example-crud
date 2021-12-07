package com.spring.example.crud.domain.services.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RefreshTokenRequest {

    @NotNull(message = "refresh_token não pode NULO.")
    @NotEmpty(message = "refresh_token invalido.")
    @JsonProperty("refresh_token")
    private String refresh;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
