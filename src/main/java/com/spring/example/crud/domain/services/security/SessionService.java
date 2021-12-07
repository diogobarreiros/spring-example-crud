package com.spring.example.crud.domain.services.security;

import com.spring.example.crud.domain.models.entity.RefreshToken;
import com.spring.example.crud.domain.repositories.RefreshTokenRepository;
import com.spring.example.crud.domain.repositories.UserRepository;
import com.spring.example.crud.domain.services.security.dto.RefreshTokenRequest;
import com.spring.example.crud.domain.services.security.dto.RefreshTokenResponse;
import com.spring.example.crud.domain.services.security.dto.TokenRequest;
import com.spring.example.crud.domain.services.security.dto.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.spring.example.crud.utils.Constants.SECURITY.*;
import static com.spring.example.crud.utils.Responses.forbidden;
import static com.spring.example.crud.utils.Responses.ok;

@Service
public class SessionService {

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Value("${token.expiration-in-hours}")
    private Integer TOKEN_EXPIRATION_IN_HOURS;

    @Value("${token.refresh.expiration-in-days}")
    private Integer REFRESH_TOKEN_EXPIRATION_IN_DAYS;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<TokenResponse> create(TokenRequest request) {
        var user = userRepository.findOptionalByEmailFetchRoles(request.getEmail())
                .filter(session -> session.validatePassword(request.getPassword()))
                    .orElseThrow(() -> forbidden(CREATE_SESSION_ERROR_MESSAGE));

        var now = LocalDateTime.now();
        var expiresIn = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);

        var token = JWT.encode(user, expiresIn, TOKEN_SECRET);
        var refresh = new RefreshToken(user, REFRESH_TOKEN_EXPIRATION_IN_DAYS);

        refreshTokenRepository.disableOldRefreshTokens(user.getId());
        
        refreshTokenRepository.save(refresh);

        var response = new TokenResponse(
            user,
            token,
            refresh,
            expiresIn
        );
        
        return ok(response);
    }

    public ResponseEntity<RefreshTokenResponse> refresh(RefreshTokenRequest request) {
        var old = refreshTokenRepository.findOptionalByCodeAndAvailableIsTrue(request.getRefresh())
            .filter(RefreshToken::nonExpired)
            .orElseThrow(() -> forbidden(REFRESH_SESSION_ERROR_MESSAGE));
        
        var now = LocalDateTime.now();
        var expiresIn = now.plusHours(TOKEN_EXPIRATION_IN_HOURS);
        var token = JWT.encode(old.getUser(), expiresIn, TOKEN_SECRET);
        
        refreshTokenRepository.disableOldRefreshTokens(old.getUser().getId());

        var refresh = refreshTokenRepository.save(new RefreshToken(old.getUser(), REFRESH_TOKEN_EXPIRATION_IN_DAYS));

        var response = new RefreshTokenResponse(
            token,
            refresh,
            expiresIn
        );
        
        return ok(response);
    }
}
