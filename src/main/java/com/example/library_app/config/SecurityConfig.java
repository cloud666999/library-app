package com.example.library_app.config;

import com.example.library_app.model.exception.AccountNotFoundException;
import com.example.library_app.model.exceptionenum.ErrorCode;
import com.example.library_app.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain basicAuthFilterChain(HttpSecurity http, AccountRepository accountRepository, SecBasicAuthenticationEntryPoint authenticationEntryPoint, SecAccesDeniedHandler accessDeniedHandler) throws  Exception   {
        http.cors(withDefaults())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .httpBasic(withDefaults())
                .userDetailsService((String username) -> accountRepository.findByUsername(username)
                        .orElseThrow(() -> new AccountNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "Account not found with username" + username)))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> authorize
                // cho phép endpoint --> public mà ko cần authen
                .requestMatchers(HttpMethod.POST, "/client/accounts/register").permitAll()
//                .requestMatchers(HttpMethod.GET , "/api/v1/book").permitAll()
                .anyRequest().authenticated());
        return http.build();

    }

}
