package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/h2-console/**",
            "/common/**",
            "/WEB-INF/views/**",
            "/login",
            "/login-form",
            "/join-form",
            "/join"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                ).csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .formLogin(form -> form.loginPage("/login-form")
                                       .loginProcessingUrl("/login")
                                       .defaultSuccessUrl("/main"));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder ecodePwd(){
        return new BCryptPasswordEncoder();
    }

/*    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }*/


}
/*
 *  /\           /\
 * /   \________/  \
 * |               |
 * | ()     ( | )  |
 * |     /\        |
 *  \______________/
 *
 *
 *
 * */