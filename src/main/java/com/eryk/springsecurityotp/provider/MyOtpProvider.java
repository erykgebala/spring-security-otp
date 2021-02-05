package com.eryk.springsecurityotp.provider;

import com.eryk.springsecurityotp.model.OtpAuth;
import com.eryk.springsecurityotp.manager.OtpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyOtpProvider  implements AuthenticationProvider {

    @Autowired
    private OtpManager otpManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();
        if (this.otpManager.getOtp(username).equals(otp)) {
            return new OtpAuth(username, otp, Arrays.asList(new SimpleGrantedAuthority("read")));
        }
        throw new BadCredentialsException("Bad otp");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OtpAuth.class.equals(aClass);
    }
}
