package com.eryk.springsecurityotp.filter;

import com.eryk.springsecurityotp.model.TokenAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyTokenFilter  extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        String token = req.getHeader("Authorization");

        TokenAuth tokenAuth = new TokenAuth(token, null);
        Authentication authenticate = authenticationManager.authenticate(tokenAuth);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        filterChain.doFilter(req, resp);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }

}

