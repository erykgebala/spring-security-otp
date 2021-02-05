package com.eryk.springsecurityotp.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuth extends UsernamePasswordAuthenticationToken {


    public TokenAuth(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public TokenAuth(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
