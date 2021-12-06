package com.spring.example.crud.middlewares;

import com.spring.example.crud.domain.services.security.SecurityService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.spring.example.crud.utils.TokenUtils.authorization;
import static java.util.Optional.ofNullable;

@Component
public class AuthorizationMiddleware extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filter
    )
        throws ServletException, IOException {

        ofNullable(authorization(request))
            .ifPresent(SecurityService::authorize);

        filter.doFilter(request, response);
    }
}