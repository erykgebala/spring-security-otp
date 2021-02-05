package com.eryk.springsecurityotp.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TokenManager {

    private Set<String> tokens = new HashSet<>();

    public void addToken(String otp) {
        this.tokens.add(otp);
    }
    public boolean checkToken(String token) {
        return this.tokens.contains(token);
    }
}
