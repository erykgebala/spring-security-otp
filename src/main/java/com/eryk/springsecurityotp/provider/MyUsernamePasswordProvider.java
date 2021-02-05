package com.eryk.springsecurityotp.provider;

import com.eryk.springsecurityotp.model.UsernamePasswordAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyUsernamePasswordProvider implements AuthenticationProvider {

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String pass = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (userDetails != null) {
            if (passwordEncoder.matches(pass, userDetails.getPassword())) {
                return new UsernamePasswordAuth(name, pass, Arrays.asList(new SimpleGrantedAuthority("read")));
            }
        }

        throw new BadCredentialsException("Bad data");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuth.class.equals(aClass);
    }
}
