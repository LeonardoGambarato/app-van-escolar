package com.appvan.backend.config;

import com.appvan.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(path.contains("/api/auth/login")){
            filterChain.doFilter(request,response);
            return;
        }

        String auth = request.getHeader("Authorization");

        if(auth == null || !auth.startsWith("Bearer ")){
            response.setStatus(401);
            return;
        }

        try{

            String token = auth.replace("Bearer ","");

            String email = jwtService.validarToken(token);

            request.setAttribute("emailLogado", email);

            var authToken =
                    new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            java.util.Collections.emptyList()
                    );

            org.springframework.security.core.context.SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);

            filterChain.doFilter(request,response);

        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(401);
        }
    }
}