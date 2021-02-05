package com.eryk.springsecurityotp;

import com.eryk.springsecurityotp.filter.MyTokenFilter;
import com.eryk.springsecurityotp.filter.MyUsernamePasswordFilter;
import com.eryk.springsecurityotp.provider.MyOtpProvider;
import com.eryk.springsecurityotp.provider.MyTokenProvider;
import com.eryk.springsecurityotp.provider.MyUsernamePasswordProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUsernamePasswordProvider myUsernamePasswordProvider;

    @Autowired
    private MyOtpProvider myOtpProvider;

    @Autowired
    private MyTokenProvider myTokenProvider;



    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
        UserDetails userDetails = User.withUsername("eryk").password("1234").authorities(new SimpleGrantedAuthority("read")).build();
        detailsManager.createUser(userDetails);
        return detailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public MyUsernamePasswordFilter myUsernamePasswordFilter() {
        return new MyUsernamePasswordFilter();
    }

    @Bean
    public MyTokenFilter myTokenFilter() {
        return new MyTokenFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myUsernamePasswordProvider)
                .authenticationProvider(myOtpProvider)
        .authenticationProvider(myTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(myUsernamePasswordFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(myTokenFilter(), BasicAuthenticationFilter.class);
    }
}
