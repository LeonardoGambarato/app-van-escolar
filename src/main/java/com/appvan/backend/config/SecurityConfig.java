package com.appvan.backend.config;

import com.appvan.backend.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login.html",
                                "/cadastro-cliente.html",
                                "/usuarios/criar-conta",
                                "/usuarios/gerar-senha",
                                "/api/auth/**"
                        ).permitAll()

                        .requestMatchers(
                                "/dashboard.html",
                                "/dashboard-admin.html",
                                "/alunos.html",
                                "/motorista.html",
                                "/financeiro.html",
                                "/rotas.html",
                                "/api/**"
                        ).authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                .formLogin(form -> form.disable());

        return http.build();
    }
}