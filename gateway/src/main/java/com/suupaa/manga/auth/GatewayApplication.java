package com.suupaa.manga.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2Login()
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/user-profile-api/user" +
                        "-profiles"));

        http.authorizeExchange()
                .anyExchange()
                .permitAll();

        http.csrf().disable();
        return http.build();
    }


}
