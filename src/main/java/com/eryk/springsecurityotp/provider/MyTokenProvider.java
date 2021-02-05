package com.eryk.springsecurityotp.provider;

import com.eryk.springsecurityotp.model.TokenAuth;
import com.eryk.springsecurityotp.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyTokenProvider implements AuthenticationProvider {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        if (tokenManager.checkToken(token)) {
            return new TokenAuth(token, null, Arrays.asList(new SimpleGrantedAuthority("read")));
        }

        throw new BadCredentialsException("Bad token");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuth.class.equals(aClass);
    }
}
