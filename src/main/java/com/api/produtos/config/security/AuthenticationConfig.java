package com.api.produtos.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class AuthenticationConfig {
    // Está classe é um Bean que vai habilitar propriedades importantes
    // O authenticationManager vai nos fornecer o método authenticate que vai permitir fazer a autenticação
    // recebendo os dados do login

    // É uma classe obrigatória para o funcionamento do spring security

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
