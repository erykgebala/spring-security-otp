package com.eryk.springsecurityotp.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OtpManager {

    private Map<String, String> otps = new HashMap<>();

    public void addOtp(String username, String otp) {
        this.otps.put(username, otp);
    }
    public String getOtp(String username) {
        return this.otps.get(username);
    }
}
