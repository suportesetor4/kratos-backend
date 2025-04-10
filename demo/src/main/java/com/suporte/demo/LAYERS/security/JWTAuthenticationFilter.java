package com.suporte.demo.LAYERS.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.suporte.demo.LAYERS.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

            // Validação dos campos obrigatórios
            if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
                throw new AuthenticationException("Login é obrigatório") {};
            }
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                throw new AuthenticationException("Senha é obrigatória") {};
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getLogin(),
                            usuario.getSenha(),
                            Collections.emptyList()));
        } catch (IOException e) {
            throw new AuthenticationException("Erro ao processar a requisição") {};
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String login = ((User) auth.getPrincipal()).getUsername();
            String token = TokenUtil.getToken(login);
            String json = String.format("{\"token\": \"%s\"}", token);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String json = String.format("{\"msg\": \"%s\"}", "Erro ao gerar token");
            response.getWriter().write(json);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage = "Credenciais inválidas";
        
        // Tratamento de diferentes tipos de erro
        if (failed.getMessage().equals("Login é obrigatório")) {
            errorMessage = "O campo login é obrigatório";
        } else if (failed.getMessage().equals("Senha é obrigatória")) {
            errorMessage = "O campo senha é obrigatório";
        } else if (failed.getCause() instanceof UsernameNotFoundException) {
            errorMessage = "Usuário não encontrado";
        } else if (failed instanceof BadCredentialsException) {
            errorMessage = "Senha ou usuario incorreta ";
        }

        String json = String.format("{\"msg\": \"%s\"}", errorMessage);
        response.getWriter().write(json);
    }
}