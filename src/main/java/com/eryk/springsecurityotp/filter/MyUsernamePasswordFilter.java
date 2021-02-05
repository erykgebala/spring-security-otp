package com.eryk.springsecurityotp.filter;

import com.eryk.springsecurityotp.model.OtpAuth;
import com.eryk.springsecurityotp.manager.OtpManager;
import com.eryk.springsecurityotp.manager.TokenManager;
import com.eryk.springsecurityotp.model.UsernamePasswordAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class MyUsernamePasswordFilter extends OncePerRequestFilter {

    @Autowired
    private OtpManager otpManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {

        String username = req.getHeader("username");
        String password = req.getHeader("password");
        String otp = req.getHeader("otp");

        if (otp == null) {
            UsernamePasswordAuth authenticationToken = new UsernamePasswordAuth(username, password);
            authenticationManager.authenticate(authenticationToken);
            String s = String.valueOf(new Random().nextInt(9999));
            System.out.println(s);
            otpManager.addOtp(username, s);
        } else {
            OtpAuth authenticationToken = new OtpAuth(username, otp);
            authenticationManager.authenticate(authenticationToken);
            String token = UUID.randomUUID().toString();
            tokenManager.addToken(token);
            resp.setHeader("Authorization", token);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

}
