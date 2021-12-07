package com.spring.example.crud.controllers.api;

import com.spring.example.crud.domain.services.security.SessionService;
import com.spring.example.crud.domain.services.security.dto.RefreshTokenRequest;
import com.spring.example.crud.domain.services.security.dto.RefreshTokenResponse;
import com.spring.example.crud.domain.services.security.dto.TokenRequest;
import com.spring.example.crud.domain.services.security.dto.TokenResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "Session")
@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

    @Autowired
    private SessionService service;

    @PostMapping
    public ResponseEntity<TokenResponse> create(@RequestBody @Valid TokenRequest request) {
        return service.create(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return service.refresh(request);
    }
}
